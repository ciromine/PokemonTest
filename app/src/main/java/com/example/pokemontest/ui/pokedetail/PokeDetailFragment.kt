package com.example.pokemontest.ui.pokedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentLoginBinding
import com.example.pokemontest.databinding.FragmentPokeDetailBinding
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.ui.navigator.Navigator
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokeDetailFragment : Fragment() {

    private var _binding: FragmentPokeDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokeDetailViewModel by viewModels()
    var pokemon: DomainPokemon? = null
    private var isFavorite = false

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = arguments?.let { PokeDetailFragmentArgs.fromBundle(it).domainPokemon }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemon?.name?.let { viewModel.getPokemonDetail(it) }
        navigator.setNavController(findNavController())
        observePokemonDetail()
        observeIsFavorite()

        binding?.ivFavorite?.setOnClickListener {
            pokemon?.let {
                viewModel.toggleFavorite()
            }
        }
        updateFavoriteIcon()
    }

    private fun observeIsFavorite() {
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFav ->
            isFavorite = isFav
            updateFavoriteIcon()
        }
    }

    private fun observePokemonDetail() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner) { result ->
            binding?.apply {
                when (result) {
                    is PokeDetailViewModel.PokeDetailResult.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is PokeDetailViewModel.PokeDetailResult.Success -> {
                        progressBar.visibility = View.GONE
                        val detail = result.pokemonDetail
                        tvName.text = detail.name
                        detail.frontDefaultSprite?.let { url ->
                            Picasso.get().load(url).fit().centerCrop().into(imageView)
                        }
                        tvAbilities.text =
                            detail.abilityNames?.joinToString(", ")
                                ?: getString(R.string.error_get_poke_detail_habilities)
                    }

                    is PokeDetailViewModel.PokeDetailResult.Error -> {
                        progressBar.visibility = View.GONE
                        val errorMessage = when (result.errorMessage) {
                            UNEXPECTED_ERROR -> getString(R.string.error_unexpected)
                            else -> result.errorMessage
                        }
                        root.let {
                            Snackbar.make(
                                it,
                                errorMessage,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun updateFavoriteIcon() {
        binding.ivFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private const val UNEXPECTED_ERROR = "unexpected_error"
