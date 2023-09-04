package com.experiment.rickandmorty.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsCharacterRepository(
        topicsRepository: OfflineFirstRepository,
    ): CharacterRepository
}