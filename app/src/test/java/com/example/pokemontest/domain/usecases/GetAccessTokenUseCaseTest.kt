package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GetAccessTokenUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val getAccessTokenUseCase = GetAccessTokenUseCase(repository)

    @Test
    fun `when invoke is called and repository returns a token, then return that token`() =
        runBlocking {
            val expectedToken = "mockAccessToken"
            stubGetToken(flowOf(expectedToken))

            val actualToken = getAccessTokenUseCase.invoke().first()

            assertEquals(expectedToken, actualToken)
        }

    @Test
    fun `when invoke is called and repository returns null, then return null`() = runBlocking {
        stubGetToken(flowOf(null))

        val actualToken = getAccessTokenUseCase.invoke().first()

        assertEquals(null, actualToken)
    }

    private fun stubGetToken(tokenFlow: Flow<String?>) {
        coEvery { repository.getToken() } returns tokenFlow
    }
}