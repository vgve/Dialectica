package com.vicgcode.dialectica.presentation

import android.app.Application
import com.vicgcode.dialectica.di.MainAppModule
import com.vicgcode.dialectica.di.MainAppModuleImpl

class MyApplication: Application() {

    companion object {
        lateinit var appModule: MainAppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = MainAppModuleImpl(this)
    }
}
