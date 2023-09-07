package com.experiment.rickandmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.experiment.rickandmorty.data.model.RemoteKeys
import com.experiment.rickandmorty.data.model.CharactersModel
import com.experiment.rickandmorty.db.dao.CharacterDao
import com.experiment.rickandmorty.db.dao.RemoteKeysDao

@Database(entities = [CharactersModel::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
