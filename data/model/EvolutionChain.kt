package com.example.myapplication.data.model

data class EvolutionChain(
    val id: Int,
    val chain: ChainLink
)

data class ChainLink(
    val species: NamedApiResource,
    val evolves_to: List<ChainLink>,
    val is_baby: Boolean,
    val evolution_details: List<EvolutionDetail>
)

data class EvolutionDetail(
    val min_level: Int?,
    val trigger: NamedApiResource,
    val item: NamedApiResource?
) 