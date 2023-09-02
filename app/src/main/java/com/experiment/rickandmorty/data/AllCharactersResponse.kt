package com.experiment.rickandmorty.data


import com.experiment.rickandmorty.data.character.CharactersModel
import com.google.gson.annotations.SerializedName

data class AllCharactersResponse(
    @SerializedName("info") var info: Info = Info(),
    @SerializedName("results") var results: List<CharactersModel> = listOf<CharactersModel>()
)