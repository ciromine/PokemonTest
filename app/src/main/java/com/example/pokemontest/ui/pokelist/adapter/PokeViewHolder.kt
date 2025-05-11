package com.example.pokemontest.ui.pokelist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontest.databinding.ViewItemPokeBinding
import com.example.pokemontest.domain.model.DomainPokemon
import com.example.pokemontest.utils.Constants
import com.squareup.picasso.Picasso

class PokeViewHolder(val binding: ViewItemPokeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(attrs: DomainPokemon, id: Int, onClickListener: (DomainPokemon) -> Unit) {
        binding.apply {
            tvPokeName.text = attrs.name
            root.setOnClickListener { onClickListener.invoke(attrs) }
            Picasso.get().load(Constants.IMAGE_URL + id + PNG).fit().centerCrop()
                .into(ivPokeImage)
        }
    }
}

private const val PNG = ".png"
