package com.example.pokemontest.data.remote

import com.example.pokemontest.data.remote.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonListResponse
}