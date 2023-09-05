package com.experiment.rickandmorty.data.character

import com.google.gson.annotations.SerializedName


data class AllCharactersResponse(
    @field:SerializedName("info") var info: Info = Info(),
    @field:SerializedName("results") var results: List<CharactersModel> = listOf<CharactersModel>()
)