package com.example.pokemontest.data.remote.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String?,
    val sprites: SpritesResponse?,
    val abilities: List<AbilityWrapperResponse>?
)