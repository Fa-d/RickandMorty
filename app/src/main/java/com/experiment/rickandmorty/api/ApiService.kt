package com.experiment.rickandmorty.api

import com.experiment.rickandmorty.data.model.GraphQLResponse
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

private interface ApiService {
    @POST("graphql/")
    @Headers("Content-Type: application/json")
    suspend fun getAllCharacter(@Body body: String): GraphQLResponse
}

private const val BASE_URL = "https://rickandmortyapi.com/"

@Singleton
class RetrofitNetwork @Inject constructor(
    okhttpCallFactory: Call.Factory,
) {

    private val networkApi = Retrofit.Builder().baseUrl(BASE_URL).callFactory(okhttpCallFactory)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)

    suspend fun getCharacters(body: String): GraphQLResponse = networkApi.getAllCharacter(body)

}
