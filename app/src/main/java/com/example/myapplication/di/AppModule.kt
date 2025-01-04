package com.example.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePokeApiService(): PokeApiService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokeApiService): PokemonRepository {
        return PokemonRepository(api)
    }

    @Provides
    fun provideGetPokemonListUseCase(repository: PokemonRepository): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }

    @Provides
    fun provideGetPokemonDetailUseCase(repository: PokemonRepository): GetPokemonDetailUseCase {
        return GetPokemonDetailUseCase(repository)
    }

    @Provides
    fun provideGetMoveDetailUseCase(repository: PokemonRepository): GetMoveDetailUseCase {
        return GetMoveDetailUseCase(repository)
    }

    @Provides
    fun provideGetAbilityDetailUseCase(repository: PokemonRepository): GetAbilityDetailUseCase {
        return GetAbilityDetailUseCase(repository)
    }
} 