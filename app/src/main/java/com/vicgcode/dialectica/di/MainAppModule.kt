package com.vicgcode.dialectica.di

import android.content.Context
import com.vicgcode.dialectica.core.data.repositories.SharedPrefsRepositoryImpl
import com.vicgcode.dialectica.core.domain.SharedPrefsKeys
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import com.vicgcode.dialectica.database.room.AppRoomDao
import com.vicgcode.dialectica.database.room.AppRoomDatabase
import com.vicgcode.dialectica.database.room.AppRoomRepository

interface MainAppModule {
    val sharedPrefsRepository: SharedPrefsRepository
    val appRoomDao: AppRoomDao
    val appRoomRepository: AppRoomRepository
}

class MainAppModuleImpl(private val context: Context): MainAppModule {

    override val sharedPrefsRepository: SharedPrefsRepository by lazy {
        SharedPrefsRepositoryImpl(
            context.getSharedPreferences(
                SharedPrefsKeys.SHARED_PREFS_KEY,
                Context.MODE_PRIVATE
            )
        )
    }

    override val appRoomDao: AppRoomDao by lazy {
        AppRoomDatabase.getInstance(context).getAppRoomDao()
    }

    override val appRoomRepository: AppRoomRepository by lazy {
        AppRoomRepository(appRoomDao)
    }
}
