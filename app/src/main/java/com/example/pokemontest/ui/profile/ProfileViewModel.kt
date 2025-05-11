package com.example.pokemontest.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.pokemontest.domain.usecases.GetTotalFavoritePokemonCountUseCase
import com.example.pokemontest.domain.usecases.GetViewedPokemonCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getViewedPokemonCountUseCase: GetViewedPokemonCountUseCase,
    getTotalFavoritePokemonCountUseCase: GetTotalFavoritePokemonCountUseCase
) : ViewModel() {

    val viewedPokemonCount = getViewedPokemonCountUseCase().asLiveData()
    val totalFavoritePokemonCount = getTotalFavoritePokemonCountUseCase().asLiveData()
}