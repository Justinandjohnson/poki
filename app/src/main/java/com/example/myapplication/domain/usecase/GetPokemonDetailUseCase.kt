package com.example.myapplication.domain.usecase// --Commented out by Inspection START (1/4/25, 2:11 PM):
// --Commented out by Inspection START (1/4/25, 2:11 PM):
////class GetPokemonDetailUseCase(
////    private val repository: PokemonRepository
////) {
//// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
//    suspend operator fun invoke(name: String): Result<com.example.myapplication.domain.model.PokemonDetailDomain> =
//        repository.getPokemonDetails(name).map { pokemon ->
//            com.example.myapplication.domain.model.PokemonDetailDomain(
//                id = pokemon.id,
//                name = pokemon.name,
//                imageUrl = pokemon.sprites.front_default,
//                stats = pokemon.stats.map { stat ->
//                    com.example.myapplication.domain.model.StatDomain(
//                        name = stat.stat.name.replace("-", " "),
//                        value = stat.base_stat
//                    )
//                },
//                types = pokemon.types.map { it.type.name },
//                abilities = pokemon.abilities.map { ability ->
//                    AbilityDomain(
//                        name = ability.ability.name.replace("-", " "),
//                        description = "", // Will be filled by additional API call
//                        isHidden = ability.is_hidden
//                    )
//                },
//                moves = pokemon.moves.map { move ->
//                    com.example.myapplication.domain.model.MoveDomain(
//                        name = move.move.name.replace("-", " "),
//                        type = "", // Will be filled by additional API call
//                        damageClass = "",
//                        power = null,
//                        accuracy = null,
//                        pp = 0,
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
                        effect = ""
                    )
                }
            )
        }
} 