package com.spigot.casestudy.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spigot.casestudy.utils.Constants.Companion.PHOTO_TABLE

@Entity(tableName = PHOTO_TABLE)
data class PhotoEntity(
    val albumId: Int = 0,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val title: String? = "",
    val url: String? = "",
    val thumbnailUrl: String? = ""
)