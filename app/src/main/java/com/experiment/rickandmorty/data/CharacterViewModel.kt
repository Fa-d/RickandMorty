package com.experiment.rickandmorty.data

import androidx.lifecycle.ViewModel
import com.experiment.rickandmorty.data.character.CharactersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    repository: ApiRepository
) : ViewModel() {
    val characterList = repository.getAllCharactersOfAPage()
    var selectedCharacterID: CharactersModel = CharactersModel()
}