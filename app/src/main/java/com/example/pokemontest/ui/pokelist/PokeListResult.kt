package com.example.pokemontest.ui.pokelist

import com.example.pokemontest.domain.model.DomainPokemon

data class PokeListResult(
    var results: List<DomainPokemon>?,
    var error: Boolean = false
)