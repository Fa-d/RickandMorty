package com.experiment.rickandmorty.work.foreservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.experiment.rickandmorty.R
import com.experiment.rickandmorty.data.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class ImageDownloader : Service() {

    @Inject
    lateinit var mainRepository: MainRepository

    override fun onBind(p0: Intent?): IBinder? = null

    companion object {
        const val CHANNEL_ID = "file_download_channel"
        const val NOTIFICATION_ID = 1
    }

    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    private fun createNotificationChannel() {
        val pendingIntent =
            PendingIntent.getActivity(this, 0, Intent(), PendingIntent.FLAG_IMMUTABLE)

        val channel =
            NotificationChannel(CHANNEL_ID, "File Download", NotificationManager.IMPORTANCE_HIGH)
        notificationBuilder.setContentTitle("Downloading Files").setContentText("Progress: 0%")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.rick).setOngoing(true)
        notificationManager.createNotificationChannel(channel)
    }


    private fun updateNotification(progress: Int) {
        notificationBuilder.setContentText("Progress: $progress%").setProgress(100, progress, false)
            .setOnlyAlertOnce(true)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotification(): Notification {
        return notificationBuilder.build()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            val allImageList = mainRepository.getAllImages()
            val prefix = "https://rickandmortyapi.com/api/character/avatar/"
            for (i in allImageList.indices) {
                val result = allImageList[i].replace(prefix, "")
                imgFetch(allImageList[i], result)
                val progress = i * 100 / allImageList.size
                updateNotification(progress)
            }
        }
        return START_STICKY
    }

    private fun imgFetch(url: String, fileName: String) {
        Log.e("fileName", fileName)
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val inputStream = BufferedInputStream(connection.inputStream)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val cacheFile = File(baseContext.filesDir, fileName).apply {
            createNewFile()
        }
        ByteArrayOutputStream().use { bos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            FileOutputStream(cacheFile).use { fos ->
                fos.write(bos.toByteArray())
            }
        }
    }
}