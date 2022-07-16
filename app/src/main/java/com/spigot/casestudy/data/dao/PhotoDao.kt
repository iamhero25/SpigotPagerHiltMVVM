package com.spigot.casestudy.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spigot.casestudy.model.PhotoEntity

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo_table ")
    fun getAllPhotos(): PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotos(photos: List<PhotoEntity>)

    @Query("DELETE FROM photo_table")
    suspend fun deleteAllImages()

}