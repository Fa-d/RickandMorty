package com.experiment.rickandmorty.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.experiment.rickandmorty.data.model.CharactersModel

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharactersModel>)

    @Query("SELECT * FROM characters")
    fun charactersByName(): PagingSource<Int, CharactersModel>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()


}
