package com.example.pokemontest.data.mapper

import com.example.pokemontest.data.remote.model.AbilityResponse
import com.example.pokemontest.data.remote.model.AbilityWrapperResponse
import com.example.pokemontest.data.remote.model.MockLoginResponse
import com.example.pokemontest.data.remote.model.PokemonDetailResponse
import com.example.pokemontest.data.remote.model.PokemonListResponse
import com.example.pokemontest.data.remote.model.PokemonResponse
import com.example.pokemontest.data.remote.model.SpritesResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class DataResponseMapperTest {

    private val mapper = DataResponseMapper()

    @Test
    fun `given PokemonListResponse when toDomain(), then DomainPokemonList`() {
        val remotePokemonListResponse = PokemonListResponse(
            listOf(
                PokemonResponse(
                    name = "bulbasaur",
                    url = "https://pokeapi.co/api/v2/pokemon/1/"
                ),
                PokemonResponse(
                    name = "ivysaur",
                    url = "https://pokeapi.co/api/v2/pokemon/2/"
                )
            )
        )

        val domainPokemonList = with(mapper) {
            remotePokemonListResponse.toDomain()
        }

        assertEquals(
            "bulbasaur",
            domainPokemonList.results[0].name
        )
        assertEquals(
            "https://pokeapi.co/api/v2/pokemon/1/",
            domainPokemonList.results[0].url
        )
        assertEquals(
            "ivysaur",
            domainPokemonList.results[1].name
        )
        assertEquals(
            "https://pokeapi.co/api/v2/pokemon/2/",
            domainPokemonList.results[1].url
        )
    }

    @Test
    fun `given PokemonDetailResponse when toDomain(), then DomainPokemonDetail`() {
        val remotePokemonDetailResponse = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            sprites = SpritesResponse(
                frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
            ),
            abilities = listOf(
                AbilityWrapperResponse(
                    ability = AbilityResponse(name = "overgrow")
                ),
                AbilityWrapperResponse(
                    ability = AbilityResponse(name = "chlorophyll")
                )
            )
        )

        val domainPokemonDetail = with(mapper) {
            remotePokemonDetailResponse.toDomain()
        }

        assertEquals(1, domainPokemonDetail.id)
        assertEquals("bulbasaur", domainPokemonDetail.name)
        assertEquals(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            domainPokemonDetail.frontDefaultSprite
        )
        assertEquals(listOf("overgrow", "chlorophyll"), domainPokemonDetail.abilityNames)
    }

    @Test
    fun `given PokemonDetailResponse with null sprites and abilities when toDomain(), then DomainPokemonDetail with nulls`() {
        val remotePokemonDetailResponse = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            sprites = null,
            abilities = null
        )

        val domainPokemonDetail = with(mapper) {
            remotePokemonDetailResponse.toDomain()
        }

        assertEquals(1, domainPokemonDetail.id)
        assertEquals("bulbasaur", domainPokemonDetail.name)
        assertEquals(null, domainPokemonDetail.frontDefaultSprite)
        assertEquals(null, domainPokemonDetail.abilityNames)
    }

    @Test
    fun `given MockLoginResponse when toDomain(), then DomainMockLogin`() {
        val remoteMockLoginResponse = MockLoginResponse(
            accessToken = "mockAccessToken",
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = "mockRefreshToken",
            scope = "read write"
        )

        val domainMockLogin = with(mapper) {
            remoteMockLoginResponse.toDomain()
        }

        assertEquals("mockAccessToken", domainMockLogin.accessToken)
        assertEquals("Bearer", domainMockLogin.tokenType)
        assertEquals(3600, domainMockLogin.expiresIn)
        assertEquals("mockRefreshToken", domainMockLogin.refreshToken)
        assertEquals("read write", domainMockLogin.scope)
    }

    @Test
    fun `given MockLoginResponse with null values when toDomain(), then DomainMockLogin with empty strings and nulls`() {
        val remoteMockLoginResponse = MockLoginResponse(
            accessToken = null,
            tokenType = null,
            expiresIn = null,
            refreshToken = null,
            scope = null
        )

        val domainMockLogin = with(mapper) {
            remoteMockLoginResponse.toDomain()
        }

        assertEquals("", domainMockLogin.accessToken)
        assertEquals("", domainMockLogin.tokenType)
        assertEquals(null, domainMockLogin.expiresIn)
        assertEquals("", domainMockLogin.refreshToken)
        assertEquals("", domainMockLogin.scope)
    }
}