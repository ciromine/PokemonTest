package com.example.pokemontest.domain.usecases

import com.example.pokemontest.data.source.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    operator fun invoke(): Flow<String?> = flow {
        emit(dataStoreManager.getAccessToken())
    }
}