package com.experiment.rickandmorty.data

import com.experiment.rickandmorty.api.ApiService
import com.experiment.rickandmorty.data.model.GraphQLResponse
import com.experiment.rickandmorty.db.MainDatabase
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val database: MainDatabase, private val network: ApiService
) {
    suspend fun getCharacters(body: String): GraphQLResponse = network.getAllCharacter(body)
    fun characterDao() = database.characterDao()
    suspend fun getIndividualCharacter(characterID: String) =
        network.getIndividualCharacter(characterID)

    suspend fun getAllImages() = database.characterDao().getAllImages()
}
