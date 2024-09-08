package com.experiment.rickandmorty

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.experiment.rickandmorty.work.workers.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG).build()

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            WorkManager.getInstance(this@MainApplication).apply {
                   enqueueUniqueWork(
                       "fetchCharacters",
                       ExistingWorkPolicy.REPLACE,
                       OneTimeWorkRequestBuilder<SyncWorker>().setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                           .setConstraints(
                               Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                                   .build()
                           ).build()
                   )
               }
        }
    }

}
