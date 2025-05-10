package com.example.pokemontest.ui.pokedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontest.core.Resource
import com.example.pokemontest.domain.model.DomainPokemonDetail
import com.example.pokemontest.domain.usecases.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokeDetailResult>()
    val pokemonDetail: LiveData<PokeDetailResult> = _pokemonDetail

    fun getPokemonDetail(name: String) {
        getPokemonDetailUseCase(name)
            .onStart {
                _pokemonDetail.value = PokeDetailResult(isLoading = true)
            }
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _pokemonDetail.value =
                            PokeDetailResult(isLoading = false, pokemonDetail = result.data)
                    }

                    is Resource.Error -> {
                        _pokemonDetail.value = PokeDetailResult(
                            isLoading = false,
                            error = true,
                            errorMessage = result.message
                        )
                    }

                    is Resource.Loading -> {
                        _pokemonDetail.value = PokeDetailResult(isLoading = true)
                    }

                    else -> {
                        _pokemonDetail.value = PokeDetailResult(
                            isLoading = false,
                            error = true,
                            errorMessage = result.message
                        )
                    }
                }
            }
            .catch { error ->
                _pokemonDetail.value = PokeDetailResult(
                    isLoading = false,
                    error = true,
                    errorMessage = error.localizedMessage ?: "An unexpected error occurred"
                )
            }
            .launchIn(viewModelScope)
    }

    data class PokeDetailResult(
        val isLoading: Boolean = false,
        val pokemonDetail: DomainPokemonDetail? = null,
        val error: Boolean = false,
        val errorMessage: String? = null
    )
}