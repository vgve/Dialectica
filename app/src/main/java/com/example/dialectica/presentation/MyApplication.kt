package com.example.dialectica.presentation

import android.app.Application
import com.example.dialectica.di.MainAppModule
import com.example.dialectica.di.MainAppModuleImpl

class MyApplication: Application() {

    companion object {
        lateinit var appModule: MainAppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = MainAppModuleImpl(this)
    }
}
