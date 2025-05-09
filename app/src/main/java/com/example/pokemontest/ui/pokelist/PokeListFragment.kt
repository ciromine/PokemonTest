package com.example.pokemontest.ui.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentPokeListBinding
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.ui.navigator.Navigator
import com.example.pokemontest.ui.pokelist.adapter.PokeListAdapter
import com.example.pokemontest.utils.hide
import com.example.pokemontest.utils.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class PokeListFragment : Fragment() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @AndroidEntryPoint
    class PokeDetailFrafment : Fragment() {

        var binding: FragmentPokeListBinding? = null

        private val viewModel by viewModels<PokeListViewModel>()

        @Inject
        lateinit var navigator: Navigator

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            showMovieList()
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            if (binding == null) {
                binding = FragmentPokeListBinding.inflate(inflater, container, false)
            }
            return binding?.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            showLoading()
            showMovieList()
        }

        private fun showLoading() {
            binding?.progressBar?.show()
        }

        private fun hideLoading() {
            binding?.progressBar?.hide()
        }

        private fun showError() {
            binding?.root?.let {
                val snackbar = Snackbar
                    .make(
                        it,
                        getString(R.string.error_get_poke_list),
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            }
        }

        private fun showMovieList() {
            viewModel.pokeList.observe(viewLifecycleOwner) { response ->
                response?.let { pokeListResult ->
                    if (!pokeListResult.error) {
                        hideLoading()
                        pokeListResult.results?.let { result ->
                            val adapter = PokeListAdapter(result) {
                                onItemCharacterTapped(it)
                            }
                            binding?.apply {
                                mainRecycler.adapter = adapter
                                mainRecycler.show()
                            }
                        }
                    } else {
                        hideLoading()
                        showError()
                    }
                }
            }
        }

        private fun onItemCharacterTapped(domainMovie: DomainPokemon) {
            binding?.let {
                navigator.goToPokeDetail(it.root, domainMovie)
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            binding = null
        }
    }
}
