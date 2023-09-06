package com.experiment.rickandmorty

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.BuildConfig
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.experiment.rickandmorty.work.initializers.Sync
import com.experiment.rickandmorty.work.workers.SyncWorker
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {


    //lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            //.setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
            .build()

    override fun onCreate() {
        super.onCreate()
        //  setupWorkManagerJob()
        Sync.initialize(this)
    }

    private fun setupWorkManagerJob() {
        val workManagerConfiguration = Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
            .build()
        WorkManager.initialize(this, workManagerConfiguration)
        val work = OneTimeWorkRequestBuilder<SyncWorker>().build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            "dsfsdf", ExistingWorkPolicy.REPLACE, work
        )
    }
}
