package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetViewedPokemonCountUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getViewedPokemonCount()
    }
}