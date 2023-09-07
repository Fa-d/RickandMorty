package com.experiment.rickandmorty.data.model.graphql


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
    @SerializedName("results") var results: List<Result> = listOf()
)

data class Result(
    @SerializedName("gender") var gender: String = "",
    @SerializedName("id") var id: String = "",
    @SerializedName("image") var image: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("species") var species: String = "",
    @SerializedName("status") var status: String = ""
)

data class Info(
    @SerializedName("next") var next: Int = 0
)