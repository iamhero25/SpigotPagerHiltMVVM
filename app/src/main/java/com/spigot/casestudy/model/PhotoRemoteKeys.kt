package com.spigot.casestudy.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spigot.casestudy.utils.Constants.Companion.PHOTO_REMOTE_KEYS_TABLE

@Entity(tableName = PHOTO_REMOTE_KEYS_TABLE)
data class PhotoRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
        )