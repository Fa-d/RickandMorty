package com.experiment.rickandmorty.work.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.experiment.rickandmorty.R
import com.experiment.rickandmorty.data.MainRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters, private val mainRepository: MainRepository
) : CoroutineWorker(context, workerParameters) {
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        traceAsync("Sync", 0) {
            try {
                val response = mainRepository.getCharacters(fetchQuery(1))
                mainRepository.characterDao().insertAll(response.data.characters.results)
                setForeground(createForegroundInfo())
                 for (i in 2..response.data.characters.info.pages) {
               // for (i in 2..3) {
                    val response = mainRepository.getCharacters(fetchQuery(i))
                    mainRepository.characterDao().insertAll(response.data.characters.results)
                }
                val imageSyncWorker = OneTimeWorkRequestBuilder<ImageSyncWorker>().build()
                WorkManager.getInstance(context)
                    .beginUniqueWork("fetchImages", ExistingWorkPolicy.REPLACE, imageSyncWorker)
                    .enqueue()
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

    @SuppressLint("MissingPermission")
    private fun createForegroundInfo(): ForegroundInfo {
        createNotificationChannel()
        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
        val notification = NotificationCompat.Builder(context, SYNC_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.scientist).setContentTitle("RM Notification")
            .setContentText("Syncing data in the background!")
            .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(intent)
            .setAutoCancel(true).build()

        NotificationManagerCompat.from(context).notify(12345676, notification)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(SYNC_NOTIFICATION_ID, notification, FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            ForegroundInfo(SYNC_NOTIFICATION_ID, notification)
        }

    }


    companion object {
        const val SYNC_NOTIFICATION_ID = 5672341
        private const val SYNC_NOTIFICATION_CHANNEL_ID = "SyncNotificationChannel"
    }

    private fun createNotificationChannel() {


        val name = "CHANNELNAME"
        val descriptionText = "CHANNELDESCRIPTION"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(SYNC_NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

}

