package com.example.pokemontest.data.source

import com.example.pokemontest.data.remote.model.PokemonListResponse

interface PokeRemote {

    suspend fun getPokemonList(limit: Int): PokemonListResponse
}