package com.example.pokemontest.data.remote

import com.example.pokemontest.data.remote.model.PokemonDetailResponse
import com.example.pokemontest.data.remote.model.PokemonListResponse
import com.example.pokemontest.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET(Constants.GET_POKEMONS)
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetailResponse
}