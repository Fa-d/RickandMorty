package com.experiment.rickandmorty.data

import android.graphics.Bitmap
import com.experiment.rickandmorty.api.ApiService
import com.experiment.rickandmorty.cache.LruDiskCache
import com.experiment.rickandmorty.data.model.GraphQLResponse
import com.experiment.rickandmorty.db.MainDatabase
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val database: MainDatabase,
    private val network: ApiService,
    private val lruDiskCache: LruDiskCache
) {
    suspend fun getCharacters(body: String): GraphQLResponse = network.getAllCharacter(body)
    fun characterDao() = database.characterDao()
    fun remoteKeysDao() = database.remoteKeysDao()
    fun putImageToCache(imageID: String, image: Bitmap) = lruDiskCache.putImage(imageID, image)
    suspend fun getAllImage(): List<Bitmap> = lruDiskCache.getAllImages()
    fun clearImageCache() = lruDiskCache.clearCache()

    suspend fun getIndividualCharacter(characterID: String) =
        network.getIndividualCharacter(characterID)
}
