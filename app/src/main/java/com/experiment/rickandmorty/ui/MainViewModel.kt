package com.experiment.rickandmorty.ui

import androidx.lifecycle.ViewModel
import com.experiment.core.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    suspend fun isDatabaseEmpty(): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext mainRepository.isDatabaseEmpty()
        }
    }
}