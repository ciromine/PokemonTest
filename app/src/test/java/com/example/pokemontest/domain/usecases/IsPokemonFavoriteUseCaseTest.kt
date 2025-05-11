package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class IsPokemonFavoriteUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val isPokemonFavoriteUseCase = IsPokemonFavoriteUseCase(repository)

    @Test
    fun `when invoke is called with a pokemonId, then return the favorite status from repository`() =
        runBlocking {
            val pokemonId = 5
            val expectedIsFavorite = true
            stubIsPokemonFavorite(pokemonId, flow { emit(expectedIsFavorite) })

            val actualIsFavorite = isPokemonFavoriteUseCase(pokemonId).first()

            assertEquals(expectedIsFavorite, actualIsFavorite)
        }

    @Test
    fun `when invoke is called with a different pokemonId, then return the corresponding favorite status`() =
        runBlocking {
            val pokemonId = 10
            val expectedIsFavorite = false
            stubIsPokemonFavorite(pokemonId, flow { emit(expectedIsFavorite) })

            val actualIsFavorite = isPokemonFavoriteUseCase(pokemonId).first()

            assertEquals(expectedIsFavorite, actualIsFavorite)
        }

    private fun stubIsPokemonFavorite(pokemonId: Int, isFavoriteFlow: Flow<Boolean>) {
        coEvery { repository.isPokemonFavorite(pokemonId) } returns isFavoriteFlow
    }
}
