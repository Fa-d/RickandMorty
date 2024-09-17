package com.experiment.core.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GraphQLResponse(
    @SerializedName("data")
    var `data`: Data = Data()
)

data class Data(
    @SerializedName("characters") var characters: Characters = Characters()
)

data class Characters(
    @SerializedName("info") var info: Info = Info(),
    @SerializedName("results") var results: List<CharactersModel> = listOf()
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
    @SerializedName("next") var next: Int? = 0, @SerializedName("pages") var pages: Int = 0
)