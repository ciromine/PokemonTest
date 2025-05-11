package com.example.pokemontest.data

import com.example.pokemontest.data.mapper.DataResponseMapper
import com.example.pokemontest.data.remote.model.MockLoginResponse
import com.example.pokemontest.data.remote.model.PokemonDetailResponse
import com.example.pokemontest.data.remote.model.PokemonListResponse
import com.example.pokemontest.data.remote.model.PokemonResponse
import com.example.pokemontest.data.source.DataStoreManager
import com.example.pokemontest.data.source.PokeRemote
import com.example.pokemontest.domain.model.DomainMockLogin
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.domain.model.DomainPokemonDetail
import com.example.pokemontest.domain.model.DomainPokemonList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PokeDataRepositoryTest {

    private val remote = mockk<PokeRemote>()
    private val dataStoreManager =
        mockk<DataStoreManager>(relaxUnitFun = true)
    private val mapper = mockk<DataResponseMapper>()

    private val repository = PokeDataRepository(remote, dataStoreManager, mapper)

    private val remotePokemonListResponse = PokemonListResponse(
        results = listOf(
            PokemonResponse(name = "bulbasaur", url = "url/1"),
            PokemonResponse(name = "ivysaur", url = "url/2")
        )
    )
    private val domainPokemonList = DomainPokemonList(
        results = listOf(
            DomainPokemon(name = "bulbasaur", url = "url/1"),
            DomainPokemon(name = "ivysaur", url = "url/2")
        )
    )

    private val remotePokemonDetailResponse = PokemonDetailResponse(
        id = 1,
        name = "bulbasaur",
        sprites = mockk(),
        abilities = emptyList()
    )
    private val domainPokemonDetail = DomainPokemonDetail(
        id = 1,
        name = "bulbasaur",
        frontDefaultSprite = null,
        abilityNames = emptyList()
    )

    private val remoteMockLoginResponse = MockLoginResponse(
        accessToken = "testToken",
        tokenType = "Bearer",
        expiresIn = 3600,
        refreshToken = "refreshToken",
        scope = "read"
    )
    private val domainMockLogin = DomainMockLogin(
        accessToken = "testToken",
        tokenType = "Bearer",
        expiresIn = 3600,
        refreshToken = "refreshToken",
        scope = "read"
    )

    @Test
    fun `given remote when getPokemonList then emits DomainPokemonList`(): Unit = runBlocking {
        stubGetPokemonList(remotePokemonListResponse)
        stubMapPokemonList(remotePokemonListResponse, domainPokemonList)

        repository.getPokemonList(20).collect { result ->
            assertEquals(domainPokemonList, result)
        }

        coVerify { remote.getPokemonList(limit = 20) }
        every { with(mapper) { remotePokemonListResponse.toDomain() } }
    }

    @Test
    fun `given remote when getPokemonDetail then emits DomainPokemonDetail`(): Unit = runBlocking {
        stubGetPokemonDetail(remotePokemonDetailResponse)
        stubMapPokemonDetail(remotePokemonDetailResponse, domainPokemonDetail)

        repository.getPokemonDetail("bulbasaur").collect { result ->
            assertEquals(domainPokemonDetail, result)
        }

        coVerify { remote.getPokemonDetail(name = "bulbasaur") }
        every { with(mapper) { remotePokemonDetailResponse.toDomain() } }
    }

    @Test
    fun `given remote when mockLogin then saves token and emits DomainMockLogin`() = runBlocking {
        stubMockLogin(remoteMockLoginResponse)
        stubMapMockLogin(remoteMockLoginResponse, domainMockLogin)

        repository.mockLogin("test@example.com", "password").collect { result ->
            assertEquals(domainMockLogin, result)
        }

        coVerify { remote.mockLogin(email = "test@example.com", pass = "password") }
        every { with(mapper) { remoteMockLoginResponse.toDomain() } }
        coVerify { dataStoreManager.saveAccessToken("testToken") }
    }

    @Test
    fun `when getToken then emits access token from DataStoreManager`() = runBlocking {
        coEvery { dataStoreManager.getAccessToken() } returns "storedToken"

        repository.getToken().collect { result ->
            assertEquals("storedToken", result)
        }

        coVerify { dataStoreManager.getAccessToken() }
    }

    @Test
    fun `when saveFavoritePokemonId then calls DataStoreManager`() = runBlocking {
        repository.saveFavoritePokemonId(5)
        coVerify { dataStoreManager.saveFavoritePokemonId(5) }
    }

    @Test
    fun `when removeFavoritePokemonId then calls DataStoreManager`() = runBlocking {
        repository.removeFavoritePokemonId(10)
        coVerify { dataStoreManager.removeFavoritePokemonId(10) }
    }

    @Test
    fun `when isPokemonFavorite then emits result from DataStoreManager`() = runBlocking {
        coEvery { dataStoreManager.isPokemonFavorite(3) } returns true

        repository.isPokemonFavorite(3).collect { result ->
            assertEquals(true, result)
        }

        coVerify { dataStoreManager.isPokemonFavorite(3) }
    }

    @Test
    fun `when incrementViewedPokemonCount then calls DataStoreManager`() = runBlocking {
        repository.incrementViewedPokemonCount()
        coVerify { dataStoreManager.incrementViewedPokemonCount() }
    }

    @Test
    fun `when getViewedPokemonCount then emits result from DataStoreManager`() = runBlocking {
        coEvery { dataStoreManager.getViewedPokemonCount() } returns 25

        repository.getViewedPokemonCount().collect { result ->
            assertEquals(25, result)
        }

        coVerify { dataStoreManager.getViewedPokemonCount() }
    }

    @Test
    fun `when getTotalFavoritePokemonCount then emits result from DataStoreManager`() =
        runBlocking {
            coEvery { dataStoreManager.getTotalFavoritePokemonCount() } returns 8

            repository.getTotalFavoritePokemonCount().collect { result ->
                assertEquals(8, result)
            }

            coVerify { dataStoreManager.getTotalFavoritePokemonCount() }
        }

    private fun stubGetPokemonList(response: PokemonListResponse) {
        coEvery { remote.getPokemonList(any()) } returns response
    }

    private fun stubMapPokemonList(remote: PokemonListResponse, domain: DomainPokemonList) {
        every { with(mapper) { remote.toDomain() } } returns domain
    }

    private fun stubGetPokemonDetail(response: PokemonDetailResponse) {
        coEvery { remote.getPokemonDetail(any()) } returns response
    }

    private fun stubMapPokemonDetail(remote: PokemonDetailResponse, domain: DomainPokemonDetail) {
        every { with(mapper) { remote.toDomain() } } returns domain
    }

    private fun stubMockLogin(response: MockLoginResponse) {
        coEvery { remote.mockLogin(any(), any()) } returns response
    }

    private fun stubMapMockLogin(remote: MockLoginResponse, domain: DomainMockLogin) {
        every { with(mapper) { remote.toDomain() } } returns domain
    }
}