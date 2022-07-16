package com.spigot.casestudy.utils

class Constants {

    companion object{

        //RETROFIT
        const val BASE_URL = "https://jsonplaceholder.typicode.com"

        //ROOM
        const val PHOTO_DATABASE = "photo_database"
        const val PHOTO_TABLE = "photo_table"
        const val PHOTO_REMOTE_KEYS_TABLE = "photo_remote_keys_table"

        //PAGE_CONFIG
        const val ITEMS_PER_PAGE = 10
        const val MAX_SIZE_BEFORE_DROP = 300
    }
}