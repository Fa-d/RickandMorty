package com.experiment.rickandmorty.data

import com.experiment.rickandmorty.data.character.CharactersModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository : Syncable {
    fun getCharacters(): Flow<List<CharactersModel>>
}