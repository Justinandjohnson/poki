package com.example.myapplication.domain.usecase// --Commented out by Inspection START (1/4/25, 2:11 PM):
// --Commented out by Inspection START (1/4/25, 2:11 PM):
////class GetMoveDetailUseCase @Inject constructor(
////    private val repository: PokemonRepository
////) {
//// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
//    suspend operator fun invoke(name: String): Result<com.example.myapplication.domain.model.MoveDomain> =
//        repository.getMoveDetails(name).map { move ->
//            com.example.myapplication.domain.model.MoveDomain(
//                name = move.name,
//                type = move.type.name,
//                damageClass = move.damage_class.name,
//                power = move.power,
//                accuracy = move.accuracy,
//                pp = move.pp,
//                effect = move.effect_entries
//                    .firstOrNull { it.language.name == "en" }
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
                    ?.short_effect ?: ""
            )
        }
} 