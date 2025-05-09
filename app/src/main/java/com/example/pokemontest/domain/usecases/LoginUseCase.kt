package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.model.DomainMockLogin
import com.example.pokemontest.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    operator fun invoke(email: String, pass: String): Flow<DomainMockLogin> = flow {
        val loginResponse = repository.mockLogin(email = email, pass = pass).first()
        emit(loginResponse)
    }
}