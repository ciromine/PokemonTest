package com.example.pokemontest.ui.di

import com.example.pokemontest.data.PokeDataRepository
import com.example.pokemontest.data.remote.PokeRemoteImpl
import com.example.pokemontest.data.source.PokeRemote
import com.example.pokemontest.domain.repository.PokeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(data: PokeDataRepository): PokeRepository {
        return data
    }

    @Singleton
    @Provides
    fun provideDataSource(dataSourceRemote: PokeRemoteImpl): PokeRemote {
        return dataSourceRemote
    }
}
