package com.example.pokemontest.data.mapper

import com.example.pokemontest.data.remote.model.MockLoginResponse
import com.example.pokemontest.data.remote.model.PokemonDetailResponse
import com.example.pokemontest.data.remote.model.PokemonListResponse
import com.example.pokemontest.data.remote.model.PokemonResponse
import com.example.pokemontest.domain.model.DomainMockLogin
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.domain.model.DomainPokemonDetail
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

    fun PokemonDetailResponse.toDomain() = DomainPokemonDetail(
        id = this.id,
        name = this.name,
        frontDefaultSprite = this.sprites?.frontDefault,
        abilityNames = this.abilities?.map { it.ability?.name }
    )

    fun MockLoginResponse.toDomain() = DomainMockLogin(
        accessToken = this.accessToken.orEmpty(),
        tokenType = this.tokenType.orEmpty(),
        expiresIn = this.expiresIn,
        refreshToken = this.refreshToken.orEmpty(),
        scope = this.scope.orEmpty(),
    )
}