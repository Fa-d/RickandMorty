package com.experiment.rickandmorty.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.experiment.rickandmorty.data.MainRepository
import com.experiment.rickandmorty.data.model.IndividualCharacterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var selectedCharacterID: Int? = null
    val individualCharacterData = MutableLiveData<IndividualCharacterResponse>()

    val characterList = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            mainRepository.characterDao().charactersByName()
        }).flow.cachedIn(viewModelScope)

    fun individualCharacterResponse(characterID: String) {
        viewModelScope.launch {
            var tempRes = IndividualCharacterResponse()
            CoroutineScope(Dispatchers.IO).launch {
                tempRes = mainRepository.getIndividualCharacter(characterID)
            }.invokeOnCompletion {
                individualCharacterData.postValue(tempRes)
            }
        }
    }
}
