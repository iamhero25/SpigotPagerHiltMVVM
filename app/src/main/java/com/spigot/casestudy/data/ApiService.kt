package com.spigot.casestudy.data

import com.spigot.casestudy.model.PhotoEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/photos")
    suspend fun getPhotoList(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): List<PhotoEntity>

}