package com.vicgcode.dialectica.core.di

import android.content.Context
import com.vicgcode.dialectica.core.data.repositories.SharedPrefsRepositoryImpl
import com.vicgcode.dialectica.core.domain.SharedPrefsKeys
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideSharedPrefsRepository(
        @ApplicationContext context: Context
    ): SharedPrefsRepository = SharedPrefsRepositoryImpl(
        context.getSharedPreferences(
            SharedPrefsKeys.SHARED_PREFS_KEY,
            Context.MODE_PRIVATE
        )
    )
}
