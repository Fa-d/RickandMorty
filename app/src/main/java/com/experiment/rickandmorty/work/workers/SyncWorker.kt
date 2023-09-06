/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.experiment.rickandmorty.work.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.experiment.rickandmorty.api.RetrofitNetwork
import com.experiment.rickandmorty.data.model.AllCharactersResponse
import com.experiment.rickandmorty.db.MainDatabase
import com.experiment.rickandmorty.work.initializers.syncForegroundInfo
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val service: RetrofitNetwork,
    val database: MainDatabase
) : CoroutineWorker(appContext, workerParams) {
    var gson: Gson? = null
        @Inject set

    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.syncForegroundInfo()
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        traceAsync("Sync", 0) {
            var isLastPage = false

            try {
                val paramObject = JSONObject()
                paramObject.put(
                    "query",
                    "{" + "  characters(page: 1) {" + "    info {" + "      next" + "    }" + "    results {" + "      id" + "      name" + "      gender" + "      status" + "      species" + "      image" + "    }" + "  }" + "}"
                )

                val allChars = gson?.fromJson(
                    service.getCharacters(paramObject.toString()).body().toString(),
                    AllCharactersResponse::class.java
                )


                for (i in 0..allChars!!.info.pages-1) {
                    paramObject.put(
                        "query",
                        "{" + "  characters(page: ${i + 1}) {" + "    info {" + "      next" + "    }" + "    results {" + "      id" + "      name" + "      gender" + "      status" + "      species" + "      image" + "    }" + "  }" + "}"
                    )
                    val allChars = gson?.fromJson(
                        service.getCharacters(paramObject.toString()).body().toString(),
                        AllCharactersResponse::class.java
                    )
                    database.characterDao().insertAll(allChars!!.results)
                    if (allChars.info.next == null) {
                        isLastPage = true
                        break
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (isLastPage) {
                Log.e("acca", "success")
                Result.success()
            } else {
                Log.e("acca", "retry")
                Result.retry()
            }
        }
    }

    companion object {
        fun startUpSyncWork() =
            OneTimeWorkRequestBuilder<DelegatingWorker>().setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(SyncConstraints).setInputData(SyncWorker::class.delegatedData())
                .build()

        private val SyncConstraints
            get() = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    }


}

