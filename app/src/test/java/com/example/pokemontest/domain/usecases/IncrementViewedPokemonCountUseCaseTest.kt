package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class IncrementViewedPokemonCountUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val incrementViewedPokemonCountUseCase = IncrementViewedPokemonCountUseCase(repository)

    @Test
    fun `when invoke is called, then calls the repository to increment the viewed count`() =
        runBlocking {
            coEvery { repository.incrementViewedPokemonCount() } coAnswers { }

            incrementViewedPokemonCountUseCase()

            coVerify(exactly = 1) { repository.incrementViewedPokemonCount() }
        }
}