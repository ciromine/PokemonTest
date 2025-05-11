package com.example.pokemontest.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN)
    private val favoritePokemonsKey = stringPreferencesKey(FAVORITE_POKEMONS)
    private val viewedPokemonCountKey = intPreferencesKey(VIEWED_POKEMON_COUNT)

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

    suspend fun incrementViewedPokemonCount() {
        dataStore.edit { preferences ->
            val currentCount = preferences[viewedPokemonCountKey] ?: 0
            preferences[viewedPokemonCountKey] = currentCount + 1
        }
    }

    suspend fun getViewedPokemonCount(): Int {
        return dataStore.data.map { preferences ->
            preferences[viewedPokemonCountKey] ?: 0
        }.first()
    }

    suspend fun getTotalFavoritePokemonCount(): Int {
        return dataStore.data.map { preferences ->
            preferences[favoritePokemonsKey]?.split(",")?.filterNot { it.isBlank() }?.count() ?: 0
        }.first()
    }
}

private const val ACCESS_TOKEN = "access_token"
private const val FAVORITE_POKEMONS = "favorite_pokemons"
private const val VIEWED_POKEMON_COUNT = "viewed_pokemon_count"
