package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.model.DomainPokemonDetail
import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class GetPokemonDetailUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val getPokemonDetailUseCase = GetPokemonDetailUseCase(repository)

    private val stubDomainPokemonDetail = DomainPokemonDetail(
        id = 1,
        name = "bulbasaur",
        frontDefaultSprite = "sprite",
        abilityNames = listOf("overgrow")
    )

    @Test
    fun `given repository returns success, then return Resource Success`() = runBlocking {
        stubGetPokemonDetail(flow { emit(stubDomainPokemonDetail) })

        val result = getPokemonDetailUseCase("bulbasaur").first().data

        assertEquals(stubDomainPokemonDetail, result)
    }

    @Test
    fun `given repository throws IOException, then return Resource Error with network message`() =
        runBlocking {
            stubGetPokemonDetail(flow { throw IOException() })
            val expectedErrorMessage = "Couldn't reach server. Check your internet connection."

            val result = getPokemonDetailUseCase("bulbasaur").first().message

            assertEquals(expectedErrorMessage, result)
        }

    private fun stubGetPokemonDetail(domainPokemonDetailFlow: Flow<DomainPokemonDetail>) {
        coEvery { repository.getPokemonDetail(any()) } returns domainPokemonDetailFlow
    }
}
