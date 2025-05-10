package com.example.pokemontest.ui.pokedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontest.core.Resource
import com.example.pokemontest.domain.model.DomainPokemonDetail
import com.example.pokemontest.domain.usecase.SaveFavoritePokemonUseCase
import com.example.pokemontest.domain.usecases.GetPokemonDetailUseCase
import com.example.pokemontest.domain.usecases.IsPokemonFavoriteUseCase
import com.example.pokemontest.domain.usecases.RemoveFavoritePokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val saveFavoritePokemonUseCase: SaveFavoritePokemonUseCase,
    private val removeFavoritePokemonUseCase: RemoveFavoritePokemonUseCase,
    private val isPokemonFavoriteUseCase: IsPokemonFavoriteUseCase
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokeDetailResult>()
    val pokemonDetail: LiveData<PokeDetailResult> = _pokemonDetail
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var currentPokemonId: Int? = null

    fun getPokemonDetail(name: String) {
        getPokemonDetailUseCase(name)
            .onStart {
                _pokemonDetail.value = PokeDetailResult.Loading
            }
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { data ->
                            _pokemonDetail.value = PokeDetailResult.Success(data)
                            currentPokemonId = data.id
                            currentPokemonId?.let { checkIsFavorite(it) }
                        }
                    }

                    is Resource.Error -> {
                        _pokemonDetail.value =
                            PokeDetailResult.Error(result.message ?: "An unexpected error occurred")
                    }

                    is Resource.Loading -> {
                        _pokemonDetail.value = PokeDetailResult.Loading
                    }

                    else -> {
                        _pokemonDetail.value =
                            PokeDetailResult.Error(result.message ?: "An unexpected error occurred")
                    }
                }
            }
            .catch { error ->
                _pokemonDetail.value =
                    PokeDetailResult.Error(error.localizedMessage ?: "An unexpected error occurred")
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite() {
        currentPokemonId?.let { id ->
            viewModelScope.launch {
                if (_isFavorite.value == true) {
                    removeFavoritePokemonUseCase(id)
                    _isFavorite.value = false
                } else {
                    saveFavoritePokemonUseCase(id)
                    _isFavorite.value = true
                }
            }
        }
    }

    private fun checkIsFavorite(pokemonId: Int) {
        isPokemonFavoriteUseCase(pokemonId)
            .onEach { isFav ->
                _isFavorite.value = isFav
            }
            .launchIn(viewModelScope)
    }

    sealed class PokeDetailResult {
        data object Loading : PokeDetailResult()
        data class Success(val pokemonDetail: DomainPokemonDetail) : PokeDetailResult()
        data class Error(val errorMessage: String) : PokeDetailResult()
    }
}
