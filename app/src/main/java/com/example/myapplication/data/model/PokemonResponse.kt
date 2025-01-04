package com.example.myapplication.data.model

data class PokemonResponse(
    val results: List<Pokemon>
)

data class PokemonColor(
    val id: Int,
    val name: String
) 