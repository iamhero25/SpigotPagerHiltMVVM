package com.spigot.casestudy.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.spigot.casestudy.data.ApiService
import com.spigot.casestudy.data.PhotoDatabase
import com.spigot.casestudy.model.PhotoEntity
import com.spigot.casestudy.model.PhotoRemoteKeys
import com.spigot.casestudy.utils.Constants.Companion.ITEMS_PER_PAGE
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class PhotoRemoteMediator @Inject constructor(
    private val photoApi: ApiService,
    private val photoDatabase: PhotoDatabase
): RemoteMediator<Int, PhotoEntity>() {

    private var photoDao = photoDatabase.photoDao()
    private var photoRemoteKeysDao = photoDatabase.photoRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PhotoEntity>): MediatorResult {
        try {
            val currentPage: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {

                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = photoApi.getPhotoList(page = currentPage, limit = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            // Store loaded data, and next key in transaction, so that
            // they're always consistent.

            photoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    photoDao.deleteAllImages()
                    photoRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { photo ->
                    PhotoRemoteKeys(
                        id = photo.id ,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                photoRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                photoDao.addPhotos(response)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoEntity>
    ): PhotoRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                photoRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PhotoEntity>
    ): PhotoRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                photoRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PhotoEntity>
    ): PhotoRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                photoRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }
    companion object {
        private const val STARTING_PAGE = 1
    }
}