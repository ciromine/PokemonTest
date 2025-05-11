package com.example.pokemontest.domain.usecases

import com.example.pokemontest.core.Resource
import com.example.pokemontest.domain.model.DomainPokemonDetail
import com.example.pokemontest.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokeRepository
) {
    operator fun invoke(name: String): Flow<Resource<DomainPokemonDetail>> = flow {
        try {
            val domainPokemonDetail = repository.getPokemonDetail(name)
            emit(Resource.Success(domainPokemonDetail.first()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected HTTP error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}