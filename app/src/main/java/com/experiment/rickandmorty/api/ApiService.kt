package com.experiment.rickandmorty.api

import com.experiment.rickandmorty.data.character.AllCharactersResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("character")
    suspend fun getAllCharacter(@Query("page") pageNo: Int?): AllCharactersResponse


    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
        fun create(): ApiService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
        }
    }
}