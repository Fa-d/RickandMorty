package com.experiment.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "characters")
data class CharactersModel(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("gender") val gender: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("species") val species: String,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("image") val image: String,

    )