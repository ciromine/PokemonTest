package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import com.example.pokemontest.domain.usecase.SaveFavoritePokemonUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveFavoritePokemonUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val saveFavoritePokemonUseCase = SaveFavoritePokemonUseCase(repository)

    @Test
    fun `when invoke is called with a pokemonId, then calls the repository to save the favorite status`() =
        runBlocking {
            val pokemonIdToSave = 12

            coEvery { repository.saveFavoritePokemonId(pokemonIdToSave) } coAnswers { }

            saveFavoritePokemonUseCase(pokemonIdToSave)

            coVerify(exactly = 1) { repository.saveFavoritePokemonId(pokemonIdToSave) }
        }

    @Test
    fun `when invoke is called with a different pokemonId, then calls the repository with that specific id`() =
        runBlocking {
            val anotherPokemonIdToSave = 45

            coEvery { repository.saveFavoritePokemonId(anotherPokemonIdToSave) } coAnswers { }

            saveFavoritePokemonUseCase(anotherPokemonIdToSave)

            coVerify(exactly = 1) { repository.saveFavoritePokemonId(anotherPokemonIdToSave) }
        }
}