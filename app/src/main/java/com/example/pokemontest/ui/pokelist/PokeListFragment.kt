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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PokeListFragment : Fragment() {

    private var _binding: FragmentPokeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PokeListViewModel>()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePokeListResult()
        viewModel.getPokeList()
    }

    private fun observePokeListResult() {
        viewModel.pokeListResult.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = if (result.isLoading) View.VISIBLE else View.GONE

            if (result.error) {
                binding.root.let {
                    Snackbar.make(
                        it,
                        result.errorMessage ?: getString(R.string.error_get_poke_list),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                result.results.let { pokemonList ->
                    val adapter = PokeListAdapter(pokemonList) { pokemon ->
                        onItemCharacterTapped(pokemon)
                    }
                    binding.mainRecycler.adapter = adapter
                    binding.mainRecycler.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onItemCharacterTapped(domainPokemon: DomainPokemon) {
        binding.root.let {
            navigator.goToPokeDetail(it, domainPokemon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}