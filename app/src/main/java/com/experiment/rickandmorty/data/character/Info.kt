package com.experiment.rickandmorty.data.character

import com.google.gson.annotations.SerializedName

data class Info(
    @field:SerializedName("count") var count: Int = 0,
    @field:SerializedName("next") var next: String = "",
    @field:SerializedName("pages") var pages: Int = 0,
    @field:SerializedName("prev") var prev: Any = Any()
)