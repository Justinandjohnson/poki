package com.example.myapplication.data.model

// --Commented out by Inspection START (1/4/25, 2:11 PM):
//data class EvolutionChain(
//    val id: Int,
//    val chain: ChainLink
//)
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)

// --Commented out by Inspection START (1/4/25, 2:11 PM):
// --Commented out by Inspection START (1/4/25, 2:11 PM):
////data class ChainLink(
////    val species: NamedApiResource,
////    val evolves_to: List<ChainLink>,
////    val is_baby: Boolean,
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
//    val evolution_details: List<EvolutionDetail>
//)
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)

data class EvolutionDetail(
    val min_level: Int?,
    val trigger: NamedApiResource,
    val item: NamedApiResource?
) 