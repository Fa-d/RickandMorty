package com.experiment.rickandmorty.api

import com.experiment.rickandmorty.data.character.AllCharactersResponse
import com.experiment.rickandmorty.data.character.CharactersModel
import okhttp3.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface ApiService {
    @GET("character")
    suspend fun getAllCharacter(@Query("page") pageNo: Int?): AllCharactersResponse
}

private const val BASE_URL = "https://rickandmortyapi.com/api/"

@Singleton
class RetrofitNiaNetwork @Inject constructor(
    networkJson: Converter.Factory,
    okhttpCallFactory: Call.Factory,
) {

    private val networkApi = Retrofit.Builder().baseUrl(BASE_URL).callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson)
        .build().create(ApiService::class.java)

    suspend fun getCharacters(pageNo: Int?): List<CharactersModel> =
        networkApi.getAllCharacter(pageNo).results

}
