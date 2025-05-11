package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoveFavoritePokemonUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val removeFavoritePokemonUseCase = RemoveFavoritePokemonUseCase(repository)

    @Test
    fun `when invoke is called with a pokemonId, then calls the repository to remove the favorite status`() =
        runBlocking {
            val pokemonIdToRemove = 7

            coEvery { repository.removeFavoritePokemonId(pokemonIdToRemove) } coAnswers { }

            removeFavoritePokemonUseCase(pokemonIdToRemove)

            coVerify(exactly = 1) { repository.removeFavoritePokemonId(pokemonIdToRemove) }
        }

    @Test
    fun `when invoke is called with a different pokemonId, then calls the repository with that specific id`() =
        runBlocking {
            val anotherPokemonIdToRemove = 23

            coEvery { repository.removeFavoritePokemonId(anotherPokemonIdToRemove) } coAnswers { }

            removeFavoritePokemonUseCase(anotherPokemonIdToRemove)

            coVerify(exactly = 1) { repository.removeFavoritePokemonId(anotherPokemonIdToRemove) }
        }
}
