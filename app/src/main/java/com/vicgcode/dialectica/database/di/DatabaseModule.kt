package com.vicgcode.dialectica.database.di

import android.content.Context
import com.vicgcode.dialectica.database.room.AppRoomDao
import com.vicgcode.dialectica.database.room.AppRoomDatabase
import com.vicgcode.dialectica.database.room.AppRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppRoomDao(
        @ApplicationContext context: Context
    ) = AppRoomDatabase.getInstance(context).getAppRoomDao()

    @Provides
    @Singleton
    fun provideRoomRepository(
        appRoomDao: AppRoomDao
    ): AppRoomRepository = AppRoomRepository(appRoomDao)
}
