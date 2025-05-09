package com.example.pokemontest.data.source

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    /*private val accessTokenKey = stringPreferencesKey("access_token")

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun getAccessToken(): String {
        val preferences = dataStore.data.first()
        return preferences[accessTokenKey]
    }*/

    // Implementa funciones similares para otros datos que quieras guardar
}