package com.dicoding.mysimplelogin.di

import android.content.Context
import com.dicoding.mysimplelogin.SessionManager
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    @Provides
    fun provideSessionManager(context: Context): SessionManager = SessionManager(context)
}