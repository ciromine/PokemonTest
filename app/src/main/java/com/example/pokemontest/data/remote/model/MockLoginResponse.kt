package com.example.pokemontest.data.remote.model

data class MockLoginResponse(
    val accessToken: String,
    val tokenType: String?,
    val expiresIn: Int?,
    val refreshToken: String?,
    val scope: String?
)