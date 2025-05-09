package com.example.pokemontest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainPokemon(
    val name: String,
    val url: String
): Parcelable