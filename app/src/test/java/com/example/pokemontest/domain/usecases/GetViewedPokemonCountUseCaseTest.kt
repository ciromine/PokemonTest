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

class GetViewedPokemonCountUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val getViewedPokemonCountUseCase = GetViewedPokemonCountUseCase(repository)

    @Test
    fun `when invoke is called, then return the viewed pokemon count from repository`() =
        runBlocking {
            val expectedCount = 25
            stubGetViewedPokemonCount(flow { emit(expectedCount) })

            val actualCount = getViewedPokemonCountUseCase().first()

            assertEquals(expectedCount, actualCount)
        }

    private fun stubGetViewedPokemonCount(countFlow: Flow<Int>) {
        coEvery { repository.getViewedPokemonCount() } returns countFlow
    }
}
