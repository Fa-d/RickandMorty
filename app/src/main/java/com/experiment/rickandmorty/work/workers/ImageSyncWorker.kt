package com.experiment.rickandmorty.work.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
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
import java.net.HttpURLConnection
import java.net.URL


@HiltWorker
class ImageSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val mainRepository: MainRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val currentImageIndex = inputData.getInt("current_image_index", 0)
        val totalImages = inputData.getInt("total_images", 50)
        try {
            val res = mainRepository.getAllImages()
            val prefix = "https://rickandmortyapi.com/api/character/avatar/"
            for (i in 0..totalImages) {
                val result = res[i].replace(prefix, "")
                imgFetch(res[i], result)
            }
            val outputData =
                Data.Builder().putInt("current_image_index", currentImageIndex + 1).build()
            Result.success(outputData)
        } catch (ex: Exception) {
            Result.retry()
        }
    }


    private fun imgFetch(url: String, fileName: String) {
        Log.e("fileName", fileName)
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val inputStream = BufferedInputStream(connection.inputStream)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val cacheFile = File(context.filesDir, fileName).apply {
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