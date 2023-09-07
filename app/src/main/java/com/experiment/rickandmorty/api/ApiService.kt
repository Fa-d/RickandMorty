package com.experiment.rickandmorty.api

import androidx.multidex.BuildConfig
import com.experiment.rickandmorty.data.model.GraphQLResponse
import com.experiment.rickandmorty.data.model.IndividualCharacterResponse
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

interface ApiService {
    @POST("graphql/")
    @Headers("Content-Type: application/json")
    suspend fun getAllCharacter(@Body body: String): GraphQLResponse

    @GET("https://rickandmortyapi.com/api/character/{id}")
    suspend fun getIndividualCharacter(@Path("id") id: String): IndividualCharacterResponse
}


