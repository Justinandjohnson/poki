package com.example.myapplication.data.api

import com.example.myapplication.data.model.PokemonResponse
import com.example.myapplication.data.model.PokemonDetail
import com.example.myapplication.data.model.PokemonSpecies
import com.example.myapplication.data.model.EvolutionChain
import com.example.myapplication.data.model.PokemonColor
import com.example.myapplication.data.model.MoveDetail
import com.example.myapplication.data.model.AbilityDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetail
    
    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpecies
    
    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(@Path("id") id: Int): EvolutionChain
    
    @GET("pokemon-color/{name}")
    suspend fun getPokemonColor(@Path("name") name: String): PokemonColor

    @GET("move/{name}")
    suspend fun getMoveDetails(@Path("name") name: String): MoveDetail

    @GET("ability/{name}")
    suspend fun getAbilityDetails(@Path("name") name: String): AbilityDetail
} 