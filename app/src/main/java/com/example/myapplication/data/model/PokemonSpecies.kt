package com.example.myapplication.data.model

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val color: NamedApiResource,
    val habitat: NamedApiResource?,
    val shape: NamedApiResource,
    val evolution_chain: EvolutionChainRef,
    val flavor_text_entries: List<FlavorText>,
    val genera: List<Genus>
)

data class NamedApiResource(
    val name: String,
    val url: String
)

// --Commented out by Inspection START (1/4/25, 2:11 PM):
// --Commented out by Inspection START (1/4/25, 2:11 PM):
////data class EvolutionChainRef(
// --Commented out by Inspection START (1/4/25, 2:11 PM):
//////    val url: String
//////)
////// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)

data class FlavorText(
    val flavor_text: String,
    val language: NamedApiResource
)

data class Genus(
    val genus: String,
    val language: NamedApiResource
) 