package com.dicoding.mymaps

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

@Suppress("unused")
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(this, getString(R.string.access_token))
    }
}