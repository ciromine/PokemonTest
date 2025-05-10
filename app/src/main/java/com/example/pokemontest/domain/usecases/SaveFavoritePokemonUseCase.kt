package com.example.pokemontest.domain.usecase

import com.example.pokemontest.domain.repository.PokeRepository
import javax.inject.Inject

class SaveFavoritePokemonUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    suspend operator fun invoke(pokemonId: Int) {
        repository.saveFavoritePokemonId(pokemonId)
    }
}