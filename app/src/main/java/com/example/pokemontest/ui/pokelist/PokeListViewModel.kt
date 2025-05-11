package com.example.pokemontest.ui.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontest.core.Resource
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.domain.usecases.GetPokemonListUseCase
import com.example.pokemontest.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private var _pokeListResult = MutableLiveData<PokeListResult>()
    val pokeListResult: LiveData<PokeListResult> = _pokeListResult

    fun getPokeList(limit: Int = Constants.TOTAL_POKEMON) {
        getPokemonListUseCase(limit = limit)
            .onStart {
                _pokeListResult.value = PokeListResult.Loading
            }
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _pokeListResult.value = PokeListResult.Success(result.data?.results ?: emptyList())
                    }

                    is Resource.Error -> {
                        _pokeListResult.value = PokeListResult.Error(result.message)
                    }

                    is Resource.Loading -> {
                        _pokeListResult.value = PokeListResult.Loading
                    }

                    else -> {
                        _pokeListResult.value = PokeListResult.Error(result.message)
                    }
                }
            }
            .catch { error ->
                _pokeListResult.value = PokeListResult.Error(error.localizedMessage ?: "An unexpected error occurred")
            }
            .launchIn(viewModelScope)
    }

    sealed class PokeListResult {
        data object Loading : PokeListResult()
        data class Success(val results: List<DomainPokemon>) : PokeListResult()
        data class Error(val errorMessage: String?) : PokeListResult()
    }
}
