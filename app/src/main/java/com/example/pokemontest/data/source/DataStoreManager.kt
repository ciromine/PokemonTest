package com.example.pokemontest.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val accessTokenKey = stringPreferencesKey("access_token")
    private val favoritePokemonsKey = stringPreferencesKey("favorite_pokemons")

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun getAccessToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[accessTokenKey]
        }.first()
    }

    suspend fun saveFavoritePokemonId(pokemonId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites =
                preferences[favoritePokemonsKey]?.split(",")?.mapNotNull { it.toIntOrNull() }
                    ?.toMutableList() ?: mutableListOf()
            if (!currentFavorites.contains(pokemonId)) {
                currentFavorites.add(pokemonId)
                preferences[favoritePokemonsKey] = currentFavorites.joinToString(",")
            }
        }
    }

    suspend fun removeFavoritePokemonId(pokemonId: Int) {
        dataStore.edit { preferences ->
            val currentFavorites =
                preferences[favoritePokemonsKey]?.split(",")?.mapNotNull { it.toIntOrNull() }
                    ?.toMutableList() ?: mutableListOf()
            currentFavorites.remove(pokemonId)
            preferences[favoritePokemonsKey] = currentFavorites.joinToString(",")
        }
    }

    suspend fun isPokemonFavorite(pokemonId: Int): Boolean {
        return dataStore.data.map { preferences ->
            preferences[favoritePokemonsKey]?.split(",")?.mapNotNull { it.toIntOrNull() }
                ?.contains(pokemonId) ?: false
        }.first()
    }
}
