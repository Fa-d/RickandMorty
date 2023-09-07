package com.experiment.rickandmorty.data.model


import com.google.gson.annotations.SerializedName

data class IndividualCharacterResponse(
    @SerializedName("created")
    var created: String = "",
    @SerializedName("episode")
    var episode: List<String> = listOf(),
    @SerializedName("gender")
    var gender: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("image")
    var image: String = "",
    @SerializedName("location")
    var location: Location = Location(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("origin")
    var origin: Origin = Origin(),
    @SerializedName("species")
    var species: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("url")
    var url: String = ""
)

data class Location(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("url")
    var url: String = ""
)

data class Origin(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("url")
    var url: String = ""
)