package com.example.pokemontest.domain.usecases

import com.example.pokemontest.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    operator fun invoke(): Flow<String?> = flow {
        val loginResponse = repository.getToken().first()
        emit(loginResponse)
    }
}