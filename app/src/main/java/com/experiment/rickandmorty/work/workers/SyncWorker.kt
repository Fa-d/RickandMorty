package com.experiment.rickandmorty.work.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.experiment.rickandmorty.R
import com.experiment.rickandmorty.data.MainRepository
import com.experiment.rickandmorty.work.foreservice.ImageDownloader
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.json.JSONObject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters, private val mainRepository: MainRepository
) : CoroutineWorker(context, workerParameters) {


    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


    override suspend fun doWork(): Result {
        try {
            val response = mainRepository.getCharacters(fetchQuery(1))
            mainRepository.characterDao().insertAll(response.data.characters.results)
            setForeground(createForegroundInfo())
            val pagesCount = response.data.characters.info.pages
            for (i in 2..pagesCount) {
                val charactersList = mainRepository.getCharacters(fetchQuery(i))
                mainRepository.characterDao().insertAll(charactersList.data.characters.results)
                val progress: Int = i * 100 / (pagesCount - 1)
                updateNotification(progress)
            }
            context.startService(Intent(context, ImageDownloader::class.java))

            return Result.success()
            } catch (e: Exception) {
                e.printStackTrace()
            return Result.retry()
            }

    }

    private fun fetchQuery(pageNo: Int): String {
        return JSONObject().put(
            "query",
            "{ characters(page: ${pageNo}) { info { next pages } results { id name gender status species image } }}"
        ).toString()
    }
    private val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)


    companion object {
        const val CHANNEL_ID = "data_fetch_channel"
        const val NOTIFICATION_ID = 1
    }


    private fun createNotification(): Notification {
        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        val pendingIntent =
            PendingIntent.getActivity(context, 0, Intent(), PendingIntent.FLAG_IMMUTABLE)

        val channel = NotificationChannel(
            CHANNEL_ID, "Data sync", NotificationManager.IMPORTANCE_HIGH
        )
        notificationBuilder.setContentTitle("Syncing data").setContentText("Progress: 0%")
            .setContentIntent(pendingIntent).setSmallIcon(R.drawable.rick).setOngoing(true)
        notificationManager.createNotificationChannel(channel)
    }


    private fun createForegroundInfo(): ForegroundInfo {
        createNotificationChannel()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ForegroundInfo(
                NOTIFICATION_ID, createNotification(), FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            ForegroundInfo(NOTIFICATION_ID, createNotification())
        }
    }

    private fun updateNotification(progress: Int) {
        notificationBuilder.setContentText("Progress: $progress%").setProgress(100, progress, false)
            .setOnlyAlertOnce(true)
        notificationManager.notify(ImageDownloader.NOTIFICATION_ID, notificationBuilder.build())
        if (progress == 100) {
            notificationBuilder.setContentText("Sync complete")
        }
    }

}

