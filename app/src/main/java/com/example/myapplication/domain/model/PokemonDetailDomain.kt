package com.example.myapplication.domain.model

data class PokemonDetailDomain(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val stats: List<StatDomain>,
    val types: List<String>,
    val abilities: List<AbilityDomain>,
    val moves: List<MoveDomain>
)

data class StatDomain(
    val name: String,
    val value: Int,
    val maxValue: Int = 255
)

// --Commented out by Inspection START (1/4/25, 2:11 PM):
//data class AbilityDomain(
//    val name: String,
//    val description: String,
//    val isHidden: Boolean
//)
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)

data class MoveDomain(
    val name: String,
    val type: String,
    val damageClass: String,
    val power: Int?,
    val accuracy: Int?,
    val pp: Int,
    val effect: String
) 