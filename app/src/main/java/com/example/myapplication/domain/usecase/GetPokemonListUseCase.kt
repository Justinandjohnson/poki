package com.example.myapplication.domain.usecase// --Commented out by Inspection START (1/4/25, 2:11 PM):
// --Commented out by Inspection START (1/4/25, 2:11 PM):
////class GetPokemonListUseCase(
////    private val repository: PokemonRepository
////) {
//// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
//    suspend operator fun invoke(): Result<List<com.example.myapplication.domain.model.PokemonDomain>> =
//        repository.getPokemonList(151).map { response ->
//            response.results.map { pokemon ->
//                com.example.myapplication.domain.model.PokemonDomain(
//                    id = com.example.myapplication.domain.usecase.getPokemonId(pokemon.url),
//                    name = pokemon.name,
//                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${com.example.myapplication.domain.usecase.getPokemonId(pokemon.url)}.png"
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
                )
            }
        }

    private fun getPokemonId(url: String): Int =
        url.split("/").dropLast(1).last().toInt()
} 