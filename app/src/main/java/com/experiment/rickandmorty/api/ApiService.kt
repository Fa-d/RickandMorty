package com.experiment.rickandmorty.api

import androidx.multidex.BuildConfig
import com.experiment.rickandmorty.data.model.GraphQLResponse
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton

interface ApiService {
    @POST("graphql/")
    @Headers("Content-Type: application/json")
    suspend fun getAllCharacter(@Body body: String): GraphQLResponse
}


