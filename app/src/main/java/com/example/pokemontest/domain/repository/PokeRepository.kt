package com.example.pokemontest.domain.repository

import com.example.pokemontest.domain.model.DomainMockLogin
import com.example.pokemontest.domain.model.DomainPokemonDetail
import com.example.pokemontest.domain.model.DomainPokemonList
import kotlinx.coroutines.flow.Flow

interface PokeRepository {
    fun getPokemonList(limit: Int): Flow<DomainPokemonList>
    fun getPokemonDetail(name: String): Flow<DomainPokemonDetail>
    fun mockLogin(email: String, pass: String): Flow<DomainMockLogin>
    fun getToken(): Flow<String?>
    suspend fun saveFavoritePokemonId(pokemonId: Int)
    suspend fun removeFavoritePokemonId(pokemonId: Int)
    fun isPokemonFavorite(pokemonId: Int): Flow<Boolean>
}