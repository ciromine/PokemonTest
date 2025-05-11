package com.example.pokemontest.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pokemontest.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewedPokemonCount()
        observeFavoritePokemonCount()
    }

    private fun observeViewedPokemonCount() {
        viewModel.viewedPokemonCount.observe(viewLifecycleOwner, Observer { count ->
            binding.tvViewedCount.text = count.toString()
        })
    }

    private fun observeFavoritePokemonCount() {
        viewModel.totalFavoritePokemonCount.observe(viewLifecycleOwner, Observer { count ->
            binding.tvFavoriteCount.text = count.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
