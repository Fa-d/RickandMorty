package com.experiment.rickandmorty.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    repository: ApiRespository
) : ViewModel() {
    val characterList = repository.getAllCharactersOfAPage()
}