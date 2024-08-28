package com.experiment.rickandmorty.api

import com.experiment.rickandmorty.data.model.GraphQLResponse
import com.experiment.rickandmorty.data.model.IndividualCharacterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("graphql/")
    @Headers("Content-Type: application/json")
    suspend fun getAllCharacter(@Body body: String): GraphQLResponse

    @GET("https://rickandmortyapi.com/api/character/{id}")
    suspend fun getIndividualCharacter(@Path("id") id: String): IndividualCharacterResponse
}


