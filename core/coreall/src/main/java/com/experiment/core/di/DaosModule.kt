package com.experiment.core.di

import com.experiment.core.db.MainDatabase
import com.experiment.core.db.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTopicsDao(database: MainDatabase): CharacterDao = database.characterDao()
}