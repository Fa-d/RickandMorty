package com.experiment.rickandmorty.data.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "characters")
data class CharactersModel(
    @PrimaryKey val id: Int,
    @ColumnInfo(defaultValue = "", name = "created") val created: String,
    @ColumnInfo(defaultValue = "", name = "gender") val gender: String,
    @ColumnInfo(defaultValue = "", name = "image") val image: String,
    @ColumnInfo(defaultValue = "", name = "name") val name: String,
    @ColumnInfo(defaultValue = "", name = "species") val species: String,
    @ColumnInfo(defaultValue = "", name = "status") val status: String,
    @ColumnInfo(defaultValue = "", name = "type") val type: String,
    @ColumnInfo(defaultValue = "", name = "url") val url: String
)