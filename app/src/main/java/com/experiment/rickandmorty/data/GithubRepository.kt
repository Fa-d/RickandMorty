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

    fun getSearchResultStream(query: String): Flow<PagingData<CharactersModel>> {

        val pagingSourceFactory = { database.characterDao().reposByName() }

        @OptIn(ExperimentalPagingApi::class) return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = object : RemoteMediator<Int, CharactersModel>() {
                override suspend fun load(
                    loadType: LoadType, state: PagingState<Int, CharactersModel>
                ): MediatorResult {
                    Log.e("sdfs", state.pages)

                    when (loadType) {
                        LoadType.REFRESH -> {}
                        LoadType.PREPEND -> {}
                        LoadType.APPEND -> {}
                    }
                    return try {
                        val apiResponse = service.getCharacters(null)
                        val repos = apiResponse
                        val endOfPaginationReached = repos.isEmpty()
                        database.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                database.remoteKeysDao().clearRemoteKeys()
                                database.characterDao().clearRepos()
                            }
                            val prevKey = if (1 == 1) null else 1 - 1
                            val nextKey = if (endOfPaginationReached) null else 1 + 1
                            val keys = repos.map {
                                RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                            }
                            database.remoteKeysDao().insertAll(keys)
                            database.characterDao().insertAll(repos)
                        }
                        MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                    } catch (exception: IOException) {
                        MediatorResult.Error(exception)
                    } catch (exception: HttpException) {
                        MediatorResult.Error(exception)
                    }
                }
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
