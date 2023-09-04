package com.experiment.rickandmorty.data

import com.experiment.rickandmorty.api.RetrofitNiaNetwork
import com.experiment.rickandmorty.data.character.CharactersModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstRepository @Inject constructor(
    private val characterDao: CharacterDao, private val network: RetrofitNiaNetwork
) : CharacterRepository {
    override fun getCharacters(): Flow<List<CharactersModel>> = characterDao.getTopicEntities()


    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =

        synchronizer.changeListSync(
            versionReader = ChangeListVersions::topicVersion,
            changeListFetcher = { currentVersion ->
                listOf(NetworkChangeList("", 1, false))
            },
            versionUpdater = { latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = characterDao::deleteTopics,
            modelUpdater = { changedIds ->
                val networkTopics = network.getCharacters(pageNo = 1)
                characterDao.upsertTopics(
                    entities = networkTopics
                )
            },
        )

}