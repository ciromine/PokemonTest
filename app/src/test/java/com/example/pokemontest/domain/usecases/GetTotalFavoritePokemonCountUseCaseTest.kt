package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GetTotalFavoritePokemonCountUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val getTotalFavoritePokemonCountUseCase =
        GetTotalFavoritePokemonCountUseCase(repository)

    @Test
    fun `when invoke is called, then return the total favorite pokemon count from repository`() =
        runBlocking {
            val expectedCount = 10
            stubGetTotalFavoritePokemonCount(flow { emit(expectedCount) })

            val actualCount = getTotalFavoritePokemonCountUseCase().first()

            assertEquals(expectedCount, actualCount)
        }

    private fun stubGetTotalFavoritePokemonCount(countFlow: Flow<Int>) {
        coEvery { repository.getTotalFavoritePokemonCount() } returns countFlow
    }
}
