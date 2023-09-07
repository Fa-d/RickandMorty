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

import com.experiment.rickandmorty.api.ApiService
import com.experiment.rickandmorty.data.model.GraphQLResponse
import com.experiment.rickandmorty.db.MainDatabase
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val database: MainDatabase, private val network: ApiService
) {
    suspend fun getCharacters(body: String): GraphQLResponse = network.getAllCharacter(body)
    fun characterDao() = database.characterDao()
    fun remoteKeysDao() = database.remoteKeysDao()
}
