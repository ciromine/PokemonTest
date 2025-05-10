package com.example.pokemontest.domain.model

data class DomainPokemonDetail(
    val id: Int,
    val name: String?,
    val frontDefaultSprite: String?,
    val abilityNames: List<String?>?
)