package com.experiment.rickandmorty

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.experiment.rickandmorty.work.workers.ImageSyncWorker
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
            /*   WorkManager.getInstance(this@MainApplication).apply {
                   enqueueUniqueWork(
                       "fetchCharacters",
                       ExistingWorkPolicy.REPLACE,
                       SyncWorker.startUpSyncWork(),
                   )
               }
            WorkManager.getInstance(this@MainApplication).beginUniqueWork(
                    "fetchCharacters", ExistingWorkPolicy.REPLACE, SyncWorker.startUpSyncWork()
                ).then(ImageSyncWorker.startUpImageSyncWork()).enqueue()*/
        }
    }

}
