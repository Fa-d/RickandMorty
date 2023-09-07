package com.experiment.rickandmorty.work.workers

import android.content.Context
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

            try {
                val response = service.getCharacters(fetchQuery(1))
                database.characterDao().insertAll(response.data.characters.results)

                for (i in 2..response.data.characters.info.pages) {
                    val response = service.getCharacters(fetchQuery(i))
                    database.characterDao().insertAll(response.data.characters.results)
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

    companion object {
        fun startUpSyncWork() =
            OneTimeWorkRequestBuilder<DelegatingWorker>().setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(SyncConstraints).setInputData(SyncWorker::class.delegatedData())
                .build()

        private val SyncConstraints
            get() = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    }


}

