package com.example.pokemontest.data

import com.example.pokemontest.data.mapper.DataResponseMapper
import com.example.pokemontest.data.source.DataStoreManager
import com.example.pokemontest.data.source.PokeRemote
import com.example.pokemontest.domain.model.DomainMockLogin
import com.example.pokemontest.domain.model.DomainPokemonDetail
import com.example.pokemontest.domain.model.DomainPokemonList
import com.example.pokemontest.domain.repository.PokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokeDataRepository @Inject constructor(
    private val remote: PokeRemote,
    private val dataStoreManager: DataStoreManager,
    private val mapper: DataResponseMapper
) : PokeRepository {

    override fun getPokemonList(limit: Int): Flow<DomainPokemonList> = flow {
        val pokeList = with(mapper) {
            remote.getPokemonList(limit = limit).toDomain()
        }
        emit(pokeList)
    }

    override fun getPokemonDetail(name: String): Flow<DomainPokemonDetail> = flow {
        val pokemon = with(mapper) {
            remote.getPokemonDetail(name = name).toDomain()
        }
        emit(pokemon)
    }

    override fun mockLogin(email: String, pass: String): Flow<DomainMockLogin> = flow {
        val domainLogin = with(mapper) {
            remote.mockLogin(
                email = email,
                pass = pass
            ).toDomain()
        }
        dataStoreManager.saveAccessToken(domainLogin.accessToken)
        emit(domainLogin)
    }

    override fun getToken(): Flow<String?> = flow {
        emit(dataStoreManager.getAccessToken())
    }

    override suspend fun saveFavoritePokemonId(pokemonId: Int) {
        dataStoreManager.saveFavoritePokemonId(pokemonId)
    }

    override suspend fun removeFavoritePokemonId(pokemonId: Int) {
        dataStoreManager.removeFavoritePokemonId(pokemonId)
    }

    override fun isPokemonFavorite(pokemonId: Int): Flow<Boolean> = flow {
        emit(dataStoreManager.isPokemonFavorite(pokemonId))
    }

    override suspend fun incrementViewedPokemonCount() {
        dataStoreManager.incrementViewedPokemonCount()
    }

    override fun getViewedPokemonCount(): Flow<Int> = flow {
        emit(dataStoreManager.getViewedPokemonCount())
    }

    override fun getTotalFavoritePokemonCount(): Flow<Int> = flow {
        emit(dataStoreManager.getTotalFavoritePokemonCount())
    }
}
