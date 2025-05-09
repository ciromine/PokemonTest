package com.example.pokemontest.ui.pokelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontest.databinding.ViewItemPokeBinding
import com.example.pokemontest.domain.model.DomainPokemon

class PokeListAdapter(
    private val items: List<DomainPokemon>,
    private val onItemClickListener: (DomainPokemon) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ViewItemPokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val summary = items[position]
        (holder as PokeViewHolder).bind(summary, onItemClickListener)
    }
}
