package com.experiment.rickandmorty.work.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.experiment.rickandmorty.data.MainRepository
import com.experiment.rickandmorty.work.initializers.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    val mainRepository: MainRepository
) : CoroutineWorker(context, workerParameters) {
    
    override suspend fun getForegroundInfo(): ForegroundInfo = context.syncForegroundInfo()
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        traceAsync("Sync", 0) {
            try {
                val response = mainRepository.getCharacters(fetchQuery(1))
                mainRepository.characterDao().insertAll(response.data.characters.results)

                for (i in 2..response.data.characters.info.pages) {
                    val response = mainRepository.getCharacters(fetchQuery(i))
                    Log.e("sdfgfh", response.toString())
                    mainRepository.characterDao().insertAll(response.data.characters.results)
                }
                Result.success()
            } catch (e: Exception) {
                e.printStackTrace()
                Result.retry()
            }
        }
    }

    private fun fetchQuery(pageNo: Int): String {
        return JSONObject().put(
            "query",
            "{ characters(page: ${pageNo}) { info { next pages } results { id name gender status species image } }}"
        ).toString()
    }

}

