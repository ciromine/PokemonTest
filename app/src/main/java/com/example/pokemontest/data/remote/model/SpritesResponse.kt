package com.example.pokemontest.data.remote.model

import com.google.gson.annotations.SerializedName

data class SpritesResponse(
    @SerializedName("back_default") val backDefault: String?
)