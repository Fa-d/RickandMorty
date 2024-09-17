package com.experiment.core.data

import com.experiment.core.api.ApiService
import com.experiment.core.data.model.GraphQLResponse
import com.experiment.core.db.MainDatabase
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val database: MainDatabase, private val network: ApiService
) {
    suspend fun getCharacters(body: String): GraphQLResponse = network.getAllCharacter(body)
    fun characterDao() = database.characterDao()
    suspend fun getIndividualCharacter(characterID: String) =
        network.getIndividualCharacter(characterID)

    suspend fun getAllImages() = database.characterDao().getAllImages()
    suspend fun isDatabaseEmpty() = database.characterDao().isDatabaseEmpty()
}
