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
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.experiment.rickandmorty.api.RetrofitNetwork
import com.experiment.rickandmorty.data.db.MainDatabase
import com.experiment.rickandmorty.work.initializers.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {


    @Inject
    lateinit var service: RetrofitNetwork

    @Inject
    lateinit var database: MainDatabase

    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        traceAsync("Sync", 0) {
            var allChars = service.getCharacters(null)
            var isLastPage = false

            for (i in 0..allChars.info.pages) {
                allChars = service.getCharacters(i + 1)
                database.characterDao().insertAll(allChars.results)
                if (allChars.info.next == null) {
                    isLastPage = true
                    break
                }
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
}
