package com.example.pokemontest.ui.pokelist

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontest.core.Resource
import com.example.pokemontest.domain.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val useCase: GetPokemonListUseCase
) : ViewModel(), LifecycleObserver {

    private var _pokeListMutable = MutableLiveData<MovieListResult>()
    val pokeList: LiveData<MovieListResult> = _pokeListMutable

    init {
        getPokeList()
    }

    private fun getPokeList() {
        useCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _pokeListMutable.value = MovieListResult(results = result.data?.results, error = false)
                }
                is Resource.Error -> {
                    _pokeListMutable.value = MovieListResult(results = emptyList(), error = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
