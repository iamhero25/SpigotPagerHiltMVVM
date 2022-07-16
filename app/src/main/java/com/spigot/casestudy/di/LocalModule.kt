package com.spigot.casestudy.di

import android.content.Context
import androidx.room.Room
import com.spigot.casestudy.data.PhotoDatabase
import com.spigot.casestudy.utils.Constants.Companion.PHOTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ):PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            PHOTO_DATABASE
        ).build()
    }

}