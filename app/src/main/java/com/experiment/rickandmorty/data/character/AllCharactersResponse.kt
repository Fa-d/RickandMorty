package com.experiment.rickandmorty.data.character


data class AllCharactersResponse(
    var info: Info = Info(), var results: List<CharactersModel> = listOf<CharactersModel>()
)