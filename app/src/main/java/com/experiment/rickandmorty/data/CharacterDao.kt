package com.experiment.rickandmorty.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.experiment.rickandmorty.data.character.CharactersModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query(value = "SELECT * FROM characters")
    fun getTopicEntities(): Flow<List<CharactersModel>>

    @Query(
        value = """
        SELECT * FROM characters
        WHERE id IN (:ids)
    """,
    )
    fun getTopicEntities(ids: Set<String>): Flow<List<CharactersModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnoreTopics(topicEntities: List<CharactersModel>): List<Long>

    @Upsert
    fun upsertTopics(entities: List<CharactersModel>)

    @Query(
        value = """
            DELETE FROM characters
            WHERE id in (:ids)
        """,
    )
    fun deleteTopics(ids: List<String>)


}
