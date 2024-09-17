package com.experiment.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.experiment.core.data.model.CharactersModel
import com.experiment.core.db.dao.CharacterDao

@Database(entities = [CharactersModel::class], version = 1, exportSchema = true)
abstract class MainDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
