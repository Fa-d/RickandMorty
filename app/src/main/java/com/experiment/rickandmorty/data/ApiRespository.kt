package com.experiment.rickandmorty.data

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.experiment.rickandmorty.api.ApiService
import com.experiment.rickandmorty.data.character.CharactersModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepository @Inject constructor(private val service: ApiService) {

    fun getAllCharactersOfAPage(): Flow<PagingData<CharactersModel>> {

        return Pager(config = PagingConfig(
            enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE
        ), pagingSourceFactory = { CharactersPagingSource(service) }).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}