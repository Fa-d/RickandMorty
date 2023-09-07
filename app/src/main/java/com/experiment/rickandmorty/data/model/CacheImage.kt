package com.experiment.rickandmorty.data.model

import android.graphics.Bitmap
import java.io.File

data class CacheImage(
    var isDiskCache: Boolean = false, var file: File, var time: Long, var bitmap: Bitmap? = null
)
