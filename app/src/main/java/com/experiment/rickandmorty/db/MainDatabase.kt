package com.experiment.rickandmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.experiment.rickandmorty.data.model.CharactersModel
import com.experiment.rickandmorty.db.dao.CharacterDao

@Database(entities = [CharactersModel::class], version = 1, exportSchema = true)
abstract class MainDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
