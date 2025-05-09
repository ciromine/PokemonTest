package com.example.pokemontest.domain

import com.example.pokemontest.core.Resource
import com.example.pokemontest.domain.model.DomainPokemonList
import com.example.pokemontest.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    operator fun invoke(limit: Int = 151): Flow<Resource<DomainPokemonList>> = flow {
        try {
            emit(Resource.Loading())

            val pokemonList = repository.getPokemonList(limit).first()
            emit(Resource.Success(pokemonList))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected HTTP error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}