package com.spigot.casestudy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spigot.casestudy.data.dao.PhotoDao
import com.spigot.casestudy.data.dao.PhotoRemoteKeyDao
import com.spigot.casestudy.model.PhotoEntity
import com.spigot.casestudy.model.PhotoRemoteKeys

@Database(entities = [PhotoEntity::class, PhotoRemoteKeys::class ], version = 1, )
abstract class PhotoDatabase: RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun photoRemoteKeysDao(): PhotoRemoteKeyDao

}