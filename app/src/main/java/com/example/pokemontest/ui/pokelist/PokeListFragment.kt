package com.example.pokemontest.ui.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentPokeListBinding
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.ui.navigator.Navigator
import com.example.pokemontest.ui.pokelist.adapter.PokeListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
        navigator.setNavController(findNavController())
        observePokeListResult()
        viewModel.getPokeList()
    }

    private fun observePokeListResult() {
        viewModel.pokeListResult.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility =
                if (result is PokeListViewModel.PokeListResult.Loading) View.VISIBLE else View.GONE

            when (result) {
                is PokeListViewModel.PokeListResult.Success -> {
                    val pokemonList = result.results
                    val pokemonIds = pokemonList.mapIndexed { index, _ -> index + 1 }
                    val adapter = PokeListAdapter(pokemonList, pokemonIds) { pokemon ->
                        onItemCharacterTapped(pokemon)
                    }
                    binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
                    binding.mainRecycler.adapter = adapter
                    binding.mainRecycler.visibility = View.VISIBLE
                }

                is PokeListViewModel.PokeListResult.Error -> {
                    binding.root.let {
                        Snackbar.make(
                            it,
                            result.errorMessage ?: getString(R.string.error_get_poke_list),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    binding.mainRecycler.visibility = View.GONE
                }

                is PokeListViewModel.PokeListResult.Loading -> {
                    binding.mainRecycler.visibility = View.GONE
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
