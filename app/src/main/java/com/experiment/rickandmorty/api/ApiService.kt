package com.experiment.rickandmorty.api

import com.experiment.rickandmorty.data.model.AllCharactersResponse
import com.experiment.rickandmorty.data.model.CharactersModel
import okhttp3.Call
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface ApiService {
    @POST("/")
    @Headers("Content-Type: application/json")
    suspend fun getAllCharacter(@Body body: String): Response<String>
}

private const val BASE_URL = "https://rickandmortyapi.com/graphql/"

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Converter.Factory,
    okhttpCallFactory: Call.Factory,
) {

    private val networkApi = Retrofit.Builder().baseUrl(BASE_URL).callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson).build().create(ApiService::class.java)

    suspend fun getCharacters(body: String): Response<String> = networkApi.getAllCharacter(body)

}
