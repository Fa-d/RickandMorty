package com.experiment.rickandmorty.work.workers

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.experiment.rickandmorty.data.MainRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


@HiltWorker
class ImageSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val mainRepository: MainRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val res = mainRepository.getAllImages()
        Log.e("res", res.size.toString())

        Result.success()
    }

    fun imgFetch() {
        val cacheDir = if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            context.getExternalCacheDir()
        } else {
            context.cacheDir
        }

        val imageUri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "my_image.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            })
        if (imageUri != null) {
            try {
                context.contentResolver.openOutputStream(imageUri).use { outputStream ->
                }
            } catch (e: IOException) {
            }
        }
        val url = ""
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = BufferedInputStream(connection.inputStream)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val cacheKey = url.hashCode().toString()
        val cacheFile = File(cacheDir, cacheKey)
        ByteArrayOutputStream().use { bos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            FileOutputStream(cacheFile).use { fos ->
                fos.write(bos.toByteArray())
            }
        }
    }
}