package com.experiment.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val repoId: Int,
    @field:SerializedName("prevKey") val prevKey: Int?,
    @field:SerializedName("nextKey") val nextKey: Int?
)
