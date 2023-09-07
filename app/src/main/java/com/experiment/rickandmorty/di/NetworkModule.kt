package com.experiment.rickandmorty.di

import androidx.multidex.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        },
    ).build()
}