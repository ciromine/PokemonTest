package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import javax.inject.Inject

class RemoveFavoritePokemonUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    suspend operator fun invoke(pokemonId: Int) {
        repository.removeFavoritePokemonId(pokemonId)
    }
}