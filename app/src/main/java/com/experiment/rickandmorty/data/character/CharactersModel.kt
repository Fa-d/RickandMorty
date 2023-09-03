package com.experiment.rickandmorty.data.character


import com.google.gson.annotations.SerializedName


data class CharactersModel(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("created") var created: String = "",
    @SerializedName("gender") var gender: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("species") var species: String = "",
    @SerializedName("status") var status: String = "",
    @SerializedName("type") var type: String = "",
    @SerializedName("url") var url: String = "",
    @SerializedName("episode") var episode: List<String> = listOf(),
    @SerializedName("location") var location: Location = Location(),
    @SerializedName("origin") var origin: Origin = Origin(),
)

data class Location(
    @SerializedName("name") var name: String = "", @SerializedName("url") var url: String = ""
)

data class Origin(
    @SerializedName("name") var name: String = "", @SerializedName("url") var url: String = ""
)