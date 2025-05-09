package com.example.pokemontest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainMockLogin(
    val accessToken: String,
    val tokenType: String?,
    val expiresIn: Int?,
    val refreshToken: String?,
    val scope: String?
): Parcelable