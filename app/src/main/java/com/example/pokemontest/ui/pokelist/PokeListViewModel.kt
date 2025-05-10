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

    init {
        getPokeList()
    }

    fun getPokeList(limit: Int = Constants.TOTAL_POKEMON) {
        getPokemonListUseCase(limit = limit)
            .onStart {
                _pokeListResult.value =
                    PokeListResult(isLoading = true, results = emptyList(), error = false)
            }
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _pokeListResult.value =
                            PokeListResult(
                                isLoading = false,
                                results = result.data?.results ?: emptyList(),
                                error = false
                            )
                    }

                    is Resource.Error -> {
                        _pokeListResult.value =
                            PokeListResult(
                                isLoading = false,
                                results = emptyList(),
                                error = true,
                                errorMessage = result.message
                            )
                    }

                    is Resource.Loading -> {
                        _pokeListResult.value =
                            PokeListResult(isLoading = true, results = emptyList(), error = false)
                    }

                    else -> {
                        _pokeListResult.value =
                            PokeListResult(
                                isLoading = false,
                                results = emptyList(),
                                error = true,
                                errorMessage = result.message
                            )
                    }
                }
            }
            .catch { error ->
                _pokeListResult.value = PokeListResult(
                    isLoading = false,
                    results = emptyList(),
                    error = true,
                    errorMessage = error.localizedMessage ?: "An unexpected error occurred"
                )
            }
            .launchIn(viewModelScope)
    }

    data class PokeListResult(
        val isLoading: Boolean = false,
        val results: List<DomainPokemon> = emptyList(),
        val error: Boolean = false,
        val errorMessage: String? = null
    )
}
