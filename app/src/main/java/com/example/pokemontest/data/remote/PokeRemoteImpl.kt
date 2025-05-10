package com.example.pokemontest.data.remote

import com.example.pokemontest.data.remote.model.MockLoginResponse
import com.example.pokemontest.data.remote.model.PokemonDetailResponse
import com.example.pokemontest.data.remote.model.PokemonListResponse
import com.example.pokemontest.data.source.PokeRemote
import javax.inject.Inject

class PokeRemoteImpl @Inject constructor(
    private val pokeApi: PokeApi,
    private val authApi: AuthApi
) :
    PokeRemote {

    override suspend fun getPokemonList(limit: Int): PokemonListResponse =
        pokeApi.getPokemonList(limit = limit)

    override suspend fun getPokemonDetail(name: String): PokemonDetailResponse {
        return pokeApi.getPokemonDetail(name)
    }

    override suspend fun mockLogin(email: String, pass: String): MockLoginResponse =
        authApi.mockLogin(
            email = email,
            pass = pass
        )
}