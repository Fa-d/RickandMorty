package com.experiment.rickandmorty

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.experiment.rickandmorty.work.workers.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.Duration
import javax.inject.Inject


@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG).build()

    val isLoadingComplete = MutableStateFlow(false)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true).setRequiresBatteryNotLow(true).build()
        val workRequest =
            PeriodicWorkRequestBuilder<SyncWorker>(Duration.ofSeconds(10))
                .setConstraints(constraints).build()

        val worker = WorkManager.getInstance(this)
        worker.enqueueUniquePeriodicWork(
            "fetchCharacters", ExistingPeriodicWorkPolicy.KEEP, workRequest
        )
        worker.getWorkInfoByIdLiveData(workRequest.id).observeForever { res ->
            when (res.state) {
                WorkInfo.State.SUCCEEDED, WorkInfo.State.ENQUEUED -> {
                    isLoadingComplete.value = true
                }
                else -> {}
            }
        }
    }
}
