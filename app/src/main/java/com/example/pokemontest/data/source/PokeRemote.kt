package com.example.pokemontest.data.source

import com.example.pokemontest.data.remote.model.MockLoginResponse
import com.example.pokemontest.data.remote.model.PokemonDetailResponse
import com.example.pokemontest.data.remote.model.PokemonListResponse

interface PokeRemote {

    suspend fun getPokemonList(limit: Int): PokemonListResponse
    suspend fun getPokemonDetail(name: String): PokemonDetailResponse
    suspend fun mockLogin(email: String, pass: String): MockLoginResponse
}