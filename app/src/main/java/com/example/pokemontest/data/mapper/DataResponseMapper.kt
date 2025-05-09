package com.example.pokemontest.data.mapper

import com.example.pokemontest.data.remote.model.MockLoginResponse
import com.example.pokemontest.data.remote.model.PokemonListResponse
import com.example.pokemontest.data.remote.model.PokemonResponse
import com.example.pokemontest.domain.model.DomainMockLogin
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.domain.model.DomainPokemonList
import javax.inject.Inject

class DataResponseMapper @Inject constructor() {

    fun PokemonListResponse.toDomain() = DomainPokemonList(
        results = results.map { it.toDomainItem() }
    )

    private fun PokemonResponse.toDomainItem() = DomainPokemon(
        name = name,
        url = url
    )

    fun MockLoginResponse.toDomain() = DomainMockLogin(
        accessToken = accessToken,
        tokenType = tokenType,
        expiresIn = expiresIn,
        refreshToken = refreshToken,
        scope = scope
    )
}