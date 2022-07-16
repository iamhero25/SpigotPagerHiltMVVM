package com.spigot.casestudy.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spigot.casestudy.data.ApiService
import com.spigot.casestudy.data.PhotoDatabase
import com.spigot.casestudy.data.paging.PhotoRemoteMediator
import com.spigot.casestudy.model.PhotoEntity
import com.spigot.casestudy.utils.Constants.Companion.ITEMS_PER_PAGE
import com.spigot.casestudy.utils.Constants.Companion.MAX_SIZE_BEFORE_DROP
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val photoApi: ApiService,
    private val photoDatabase: PhotoDatabase
) {

    fun getAllPhotos(): Flow<PagingData<PhotoEntity>> {
        val pagingSourceFactory = { photoDatabase.photoDao().getAllPhotos() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, maxSize = MAX_SIZE_BEFORE_DROP, enablePlaceholders = true),
            remoteMediator = PhotoRemoteMediator(
                photoApi = photoApi,
                photoDatabase = photoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}