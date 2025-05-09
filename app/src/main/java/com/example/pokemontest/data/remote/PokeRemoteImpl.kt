package com.example.pokemontest.data.remote

import com.example.pokemontest.data.remote.model.PokemonListResponse
import com.example.pokemontest.data.source.PokeRemote
import javax.inject.Inject

class PokeRemoteImpl @Inject constructor(private val restApi: PokeApi) :
    PokeRemote {

    override suspend fun getPokemonList(limit: Int): PokemonListResponse =
        restApi.getPokemonList(limit = limit)
}