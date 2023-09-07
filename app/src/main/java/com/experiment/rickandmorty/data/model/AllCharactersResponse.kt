package com.experiment.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class AllCharactersResponse(
    @SerializedName("info") var info: Info = Info(),
    @SerializedName("results") var results: List<CharactersModel> = listOf<CharactersModel>()
)

@Entity(tableName = "characters")
data class CharactersModel(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("gender") val gender: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("species") val species: String,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("image") val image: String,

    )

data class Info(
    @SerializedName("count") var count: Int = 0,
    @SerializedName("next") var next: Any? = null,
    @SerializedName("pages") var pages: Int = 0,
    @SerializedName("prev") var prev: Any? = null
)