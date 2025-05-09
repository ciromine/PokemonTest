package com.example.pokemontest.ui.navigator

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.ui.pokelist.PokeListFragmentDirections
import javax.inject.Inject

class Navigator @Inject constructor() {

    fun goToPokeDetail(view: View, domainPokemon: DomainPokemon) {
        val direction =
            PokeListFragmentDirections.actionPokeListFragmentToPokeDetailFragment(domainPokemon)
        safeNavigation(view, direction)
    }

    private fun safeNavigation(view: View, direction: NavDirections) {
        view.findNavController().currentDestination?.getAction(direction.actionId)
            ?.let { view.findNavController().navigate(direction) }
    }
}