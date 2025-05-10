package com.example.pokemontest.ui.pokedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentPokeDetailBinding
import com.example.pokemontest.domain.model.DomainPokemon
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeDetailFragment : Fragment() {

    var binding: FragmentPokeDetailBinding? = null
    private val viewModel: PokeDetailViewModel by viewModels()
    var pokemon: DomainPokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = arguments?.let { PokeDetailFragmentArgs.fromBundle(it).domainPokemon }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokeDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemon?.name?.let { viewModel.getPokemonDetail(it) }
        observePokemonDetail()
    }

    private fun observePokemonDetail() {
        viewModel.pokemonDetail.observe(viewLifecycleOwner) { result ->
            binding?.apply {
                progressBar.visibility = if (result.isLoading) View.VISIBLE else View.GONE
                result.pokemonDetail?.let { detail ->
                    tvName.text = detail.name
                    detail.backDefaultSprite?.let { url ->
                        Picasso.get().load(url).fit().centerCrop().into(imageView)
                    }
                    tvAbilities.text = detail.abilityNames?.joinToString(", ")
                        ?: getString(R.string.error_get_poke_detail_habilities)
                }
                if (result.error) {
                    root.let {
                        Snackbar.make(
                            it,
                            result.errorMessage ?: getString(R.string.error_get_poke_detail),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
