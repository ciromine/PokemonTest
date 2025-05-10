package com.example.pokemontest.ui.pokelist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontest.databinding.ViewItemPokeBinding
import com.example.pokemontest.domain.model.DomainPokemon
import com.squareup.picasso.Picasso

class PokeViewHolder(val binding: ViewItemPokeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(attrs: DomainPokemon, onClickListener: (DomainPokemon) -> Unit) {
        binding.apply {
            title.text = attrs.name
            root.setOnClickListener { onClickListener.invoke(attrs) }
            /*Picasso.get().load(Constants.baseUrlImages + attrs.posterPath)
                .fit().centerCrop()
                .into(image)*/
        }
    }
}
