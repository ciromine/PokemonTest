package com.example.pokemontest.ui.di

import com.example.pokemontest.data.remote.AuthApi
import com.example.pokemontest.data.remote.PokeApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Reusable
    @Provides
    fun providePokeApiService(@Named("pokeApiRetrofit") retrofit: Retrofit): PokeApi {
        return retrofit.create(PokeApi::class.java)
    }

    @Reusable
    @Provides
    fun provideAuthService(@Named("authApiRetrofit") retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}