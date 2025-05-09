package com.example.pokemontest.domain.repository

import com.example.pokemontest.domain.model.DomainPokemonList
import kotlinx.coroutines.flow.Flow

interface PokeRepository {
    fun getPokemonList(limit: Int): Flow<DomainPokemonList>
}