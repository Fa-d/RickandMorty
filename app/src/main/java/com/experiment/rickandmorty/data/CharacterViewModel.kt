package com.experiment.rickandmorty.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.experiment.rickandmorty.data.model.CharactersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var selectedCharacterID: CharactersModel? = null

    val characterList = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            mainRepository.characterDao().reposByName()
        }).flow.cachedIn(viewModelScope)

}