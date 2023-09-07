package com.experiment.rickandmorty.di

import android.content.Context
import com.experiment.rickandmorty.cache.LruDiskCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideLRUCache(@ApplicationContext app: Context): LruDiskCache {
        return LruDiskCache(app)
    }
}