package com.experiment.rickandmorty.work.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.experiment.rickandmorty.data.MainRepository
import com.experiment.rickandmorty.work.initializers.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@HiltWorker
class ImageSyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters, private val mainRepository: MainRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.syncForegroundInfo()
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        traceAsync("Sync", 0) {
            try {
                for (tempItem in mainRepository.characterDao().charactersForImageByName()) {
                    val imageBitmap = loadImageBitmap(URL(tempItem.image))
                    mainRepository.putImageToCache(tempItem.id.toString(), imageBitmap)
                }
                Log.e("Imagefetch", "success")
                Result.success()
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("Imagefetch", "failed")
                Result.retry()
            }
        }
    }

    companion object {
        fun startUpImageSyncWork() =
            OneTimeWorkRequestBuilder<DelegatingWorker>().setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(SyncConstraints)
                .setInputData(ImageSyncWorker::class.delegatedData()).build()

        private val SyncConstraints
            get() = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()


    }

    private suspend fun loadImageBitmap(url: URL): Bitmap = withContext(Dispatchers.IO) {
        BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }
}