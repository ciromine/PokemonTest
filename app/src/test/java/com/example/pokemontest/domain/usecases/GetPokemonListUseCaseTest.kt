package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.domain.model.DomainPokemonList
import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException

class GetPokemonListUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val getPokemonListUseCase = GetPokemonListUseCase(repository)

    private val stubDomainPokemonList = DomainPokemonList(
        results = listOf(
            DomainPokemon(name = "bulbasaur", url = "url1"),
            DomainPokemon(name = "ivysaur", url = "url2")
        )
    )

    @Test
    fun `given repository returns success, then return Resource Success with pokemon list`() =
        runBlocking {
            stubGetPokemonList(flow { emit(stubDomainPokemonList) })

            val result = getPokemonListUseCase().first().data

            assertEquals(stubDomainPokemonList, result)
        }

    @Test
    fun `given repository throws IOException, then return Resource Error with network message`() =
        runBlocking {
            stubGetPokemonList(flow { throw IOException() })
            val expectedErrorMessage = "Couldn't reach server. Check your internet connection."

            val result = getPokemonListUseCase().first().message

            assertEquals(expectedErrorMessage, result)
        }

    private fun stubGetPokemonList(domainPokemonListFlow: Flow<DomainPokemonList>) {
        coEvery { repository.getPokemonList(any()) } returns domainPokemonListFlow
    }
}