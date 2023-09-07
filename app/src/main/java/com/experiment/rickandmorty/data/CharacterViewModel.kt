package com.experiment.rickandmorty.data

import androidx.lifecycle.ViewModel
import com.experiment.rickandmorty.data.model.CharactersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    repository: MainRepository
) : ViewModel() {
    val characterList = repository.getCharactersStream()
    var selectedCharacterID: CharactersModel? = null
}