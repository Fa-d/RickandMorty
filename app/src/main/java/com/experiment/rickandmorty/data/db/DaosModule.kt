package com.experiment.rickandmorty.data.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: MainDatabase,
    ): CharacterDao = database.characterDao()

    @Provides
    fun providesRemoteKeysDao(
        database: MainDatabase,
    ): RemoteKeysDao = database.remoteKeysDao()
}