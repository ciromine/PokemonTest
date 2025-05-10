package com.example.pokemontest.ui.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.pokemontest.data.PokeDataRepository
import com.example.pokemontest.data.remote.PokeRemoteImpl
import com.example.pokemontest.data.source.PokeRemote
import com.example.pokemontest.domain.repository.PokeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

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

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
