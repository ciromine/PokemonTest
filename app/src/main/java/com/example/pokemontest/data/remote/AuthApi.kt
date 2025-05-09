package com.example.pokemontest.data.remote

import com.example.pokemontest.data.remote.model.MockLoginResponse
import com.example.pokemontest.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET(Constants.LOGIN_URL)
    suspend fun mockLogin(
        @Query("email") email: String,
        @Query("pass") pass: String
    ): MockLoginResponse
}