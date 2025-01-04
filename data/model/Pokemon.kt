package com.example.myapplication.data.model

data class Pokemon(
    val name: String,
    val url: String,
    val sprites: Sprites?
)

data class Sprites(
    val front_default: String,
    val front_shiny: String? = null,
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_female: String? = null,
    val front_shiny_female: String? = null,
    val back_female: String? = null,
    val back_shiny_female: String? = null
) 