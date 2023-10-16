package com.samurai.morseencoder.hilt

import android.content.Context
import android.content.SharedPreferences
import com.samurai.morseencoder.services.storage.LocalStorageKey
import com.samurai.morseencoder.services.storage.LocalStorageService
import com.samurai.morseencoder.services.storage.LocalStorageServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    fun provideLocalStorageService(sharedPreferences: SharedPreferences): LocalStorageService {
        return LocalStorageServiceImpl(sharedPreferences = sharedPreferences)
    }

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(LocalStorageKey.APP_PREFERENCES, Context.MODE_PRIVATE)
    }
}