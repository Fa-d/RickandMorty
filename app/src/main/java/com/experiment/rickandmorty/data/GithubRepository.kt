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

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.experiment.rickandmorty.api.RetrofitNetwork
import com.experiment.rickandmorty.data.db.MainDatabase
import com.experiment.rickandmorty.data.model.CharactersModel
import com.experiment.rickandmorty.data.model.RemoteKeys
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Repository class that works with local and remote data sources.
 */
class GithubRepository @Inject constructor(
    private val service: RetrofitNetwork, private val database: MainDatabase
) {

    fun getSearchResultStream(): Flow<PagingData<CharactersModel>> {

        val pagingSourceFactory = { database.characterDao().reposByName() }

        @OptIn(ExperimentalPagingApi::class) return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = object : RemoteMediator<Int, CharactersModel>() {
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
                        val apiResponse = service.getCharacters(page).results

                        val repos = apiResponse
                        val endOfPaginationReached = repos.isEmpty()
                        database.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                database.remoteKeysDao().clearRemoteKeys()
                                database.characterDao().clearRepos()
                            }
                            val prevKey = if (page == 1) null else page - 1
                            val nextKey = if (endOfPaginationReached) null else page + 1
                            val keys = repos.map {
                                RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                            }
                            database.remoteKeysDao().insertAll(keys)
                            database.characterDao().insertAll(repos)
                        }
                        return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                    } catch (exception: IOException) {
                        return MediatorResult.Error(exception)
                    } catch (exception: HttpException) {
                        return MediatorResult.Error(exception)
                    }
                }
            },
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
