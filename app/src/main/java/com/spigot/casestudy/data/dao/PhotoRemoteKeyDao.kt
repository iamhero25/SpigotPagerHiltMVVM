package com.spigot.casestudy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spigot.casestudy.model.PhotoRemoteKeys

@Dao
interface PhotoRemoteKeyDao {

    @Query("SELECT * FROM photo_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): PhotoRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PhotoRemoteKeys>)

    @Query("DELETE FROM photo_remote_keys_table")
    suspend fun deleteAllRemoteKeys()



}