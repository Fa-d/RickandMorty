package com.experiment.rickandmorty.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.experiment.rickandmorty.data.character.CharactersModel

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<CharactersModel>)

    @Query("SELECT * FROM characters")
    fun reposByName(): PagingSource<Int, CharactersModel>

    @Query("DELETE FROM characters")
    suspend fun clearRepos()


}
