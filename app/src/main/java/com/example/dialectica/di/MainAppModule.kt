package com.example.dialectica.di

import android.content.Context
import com.example.dialectica.core.data.repositories.SharedPrefsRepositoryImpl
import com.example.dialectica.core.domain.SharedPrefsKeys
import com.example.dialectica.core.domain.repositories.SharedPrefsRepository

interface MainAppModule {
    val sharedPrefsRepository: SharedPrefsRepository
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
}
