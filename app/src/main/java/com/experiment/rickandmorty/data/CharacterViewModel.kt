package com.experiment.rickandmorty.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.experiment.rickandmorty.data.model.CharactersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    val mainRepository: MainRepository
) : ViewModel() {
    val characterList = getCharactersStream()
    var selectedCharacterID: CharactersModel? = null

    private fun getCharactersStream(): Flow<PagingData<CharactersModel>> {

        val pagingSourceFactory = { mainRepository.characterDao().reposByName() }

        @OptIn(ExperimentalPagingApi::class) return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = false
            ),/* remoteMediator = object : RemoteMediator<Int, CharactersModel>() {
                 override suspend fun load(
                     loadType: LoadType, state: PagingState<Int, CharactersModel>
                 ): MediatorResult {
                     val page = when (loadType) {
                         LoadType.REFRESH -> {
                             val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                             remoteKeys?.nextKey?.minus(1) ?: 1
                         }

                         LoadType.PREPEND -> {
                             val remoteKeys = getRemoteKeyForFirstItem(state)
                             val prevKey = remoteKeys?.prevKey
                             if (prevKey == null) {
                                 return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                             }
                             prevKey
                         }

                         LoadType.APPEND -> {
                             val remoteKeys = getRemoteKeyForLastItem(state)
                             val nextKey = remoteKeys?.nextKey
                             if (nextKey == null) {
                                 return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                             }
                             nextKey
                         }
                     }
                     try {

                         var paramObject = JSONObject()
                         paramObject.put(
                             "query",
                             "{" + "  characters(page: ${page + 1}) {" + "    info {" + "      next" + "    }" + "    results {" + "      id" + "      name" + "      gender" + "      status" + "      species" + "      image" + "    }" + "  }" + "}"
                         )
                         return MediatorResult.Success(endOfPaginationReached = false)
                     } catch (exception: IOException) {
                         return MediatorResult.Error(exception)
                     } catch (exception: HttpException) {
                         return MediatorResult.Error(exception)
                     }
                 }
             },*/
            pagingSourceFactory = pagingSourceFactory
        ).flow.cachedIn(viewModelScope)
    }
}