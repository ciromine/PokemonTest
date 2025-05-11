package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.model.DomainMockLogin
import com.example.pokemontest.domain.repository.PokeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginUseCaseTest {

    private val repository = mockk<PokeRepository>()
    private val loginUseCase = LoginUseCase(repository)

    private val stubDomainMockLogin = DomainMockLogin(
        accessToken = "mockAccessToken",
        tokenType = "Bearer",
        expiresIn = 3600,
        refreshToken = "mockRefreshToken",
        scope = "read write"
    )

    @Test
    fun `when invoke is called with email and password, then return the login response from repository`() =
        runBlocking {
            val email = "test@example.com"
            val password = "password123"
            stubMockLogin(email, password, flow { emit(stubDomainMockLogin) })

            val actualLoginResponse = loginUseCase(email, password).first()

            assertEquals(stubDomainMockLogin, actualLoginResponse)
        }

    @Test
    fun `when invoke is called with different credentials, then return the corresponding login response`() =
        runBlocking {
            val email = "another@example.com"
            val password = "securePassword"
            val anotherStubDomainMockLogin = DomainMockLogin(
                accessToken = "anotherToken",
                tokenType = "Bearer",
                expiresIn = 7200,
                refreshToken = "anotherRefreshToken",
                scope = "read"
            )
            stubMockLogin(email, password, flow { emit(anotherStubDomainMockLogin) })

            val actualLoginResponse = loginUseCase(email, password).first()

            assertEquals(anotherStubDomainMockLogin, actualLoginResponse)
        }

    private fun stubMockLogin(
        email: String,
        pass: String,
        domainMockLoginFlow: Flow<DomainMockLogin>
    ) {
        coEvery { repository.mockLogin(email = email, pass = pass) } returns domainMockLoginFlow
    }
}
