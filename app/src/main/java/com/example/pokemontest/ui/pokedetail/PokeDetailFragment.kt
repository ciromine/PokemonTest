package com.example.pokemontest.ui.pokedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokemontest.databinding.FragmentPokeDetailBinding
import com.example.pokemontest.domain.model.DomainPokemon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class PokeDetailFragment : Fragment() {

    var binding: FragmentPokeDetailBinding? = null

    var pokemon: DomainPokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = arguments?.let { PokeDetailFragmentArgs.fromBundle(it).domainPokemon }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentPokeDetailBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            /*tvDate.text = movie?.releaseDate
            tvDescription.text = movie?.overview
            Picasso.get().load(Constants.baseUrlImages + movie?.posterPath)
                .fit().centerCrop()
                .into(imageView)*/
        }
    }
}

