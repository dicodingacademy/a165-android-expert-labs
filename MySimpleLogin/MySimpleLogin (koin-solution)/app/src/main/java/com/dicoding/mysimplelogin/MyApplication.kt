package com.dicoding.mysimplelogin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(storageModule)
        }
    }
}