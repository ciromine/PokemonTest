package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsPokemonFavoriteUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    operator fun invoke(pokemonId: Int): Flow<Boolean> {
        return repository.isPokemonFavorite(pokemonId)
    }
}