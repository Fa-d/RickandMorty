/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.experiment.rickandmorty.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import com.experiment.rickandmorty.data.model.RemoteKeys
import com.experiment.rickandmorty.data.model.CharactersModel
import com.experiment.rickandmorty.db.MainDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val database: MainDatabase) {

    fun getCharactersStream(): Flow<PagingData<CharactersModel>> {

        val pagingSourceFactory = { database.characterDao().reposByName() }

        @OptIn(ExperimentalPagingApi::class) return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),/* remoteMediator = object : RemoteMediator<Int, CharactersModel>() {
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
        ).flow
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharactersModel>
    ): RemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharactersModel>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.remoteKeysDao().remoteKeysRepoId(repo.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharactersModel>): RemoteKeys? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
            database.remoteKeysDao().remoteKeysRepoId(repo.id)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
