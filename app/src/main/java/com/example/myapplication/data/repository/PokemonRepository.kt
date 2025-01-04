package com.example.myapplication.data.repository

import com.example.myapplication.data.api.PokeApiService
import com.example.myapplication.data.model.*

class PokemonRepository(private val pokeApiService: PokeApiService) {
    private val cache = mutableMapOf<String, PokemonDetail>()
    private val speciesCache = mutableMapOf<Int, PokemonSpecies>()
    private val evolutionChainCache = mutableMapOf<Int, EvolutionChain>()
    private val moveCache = mutableMapOf<String, MoveDetail>()
    private val abilityCache = mutableMapOf<String, AbilityDetail>()

    suspend fun getPokemonList(limit: Int): Result<PokemonResponse> = 
        runCatching { 
            pokeApiService.getPokemonList(limit) 
        }

    suspend fun getPokemonDetails(name: String): Result<PokemonDetail> = 
        runCatching {
            cache.getOrPut(name) { 
                pokeApiService.getPokemonDetails(name)
            }
        }

// --Commented out by Inspection START (1/4/25, 2:11 PM):
//    suspend fun getPokemonSpecies(id: Int): Result<PokemonSpecies> =
//        runCatching {
//            speciesCache.getOrPut(id) {
//                pokeApiService.getPokemonSpecies(id)
//            }
//        }
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)

// --Commented out by Inspection START (1/4/25, 2:11 PM):
//    suspend fun getEvolutionChain(id: Int): Result<EvolutionChain> =
//        runCatching {
//            evolutionChainCache.getOrPut(id) {
// --Commented out by Inspection START (1/4/25, 2:11 PM):
////                pokeApiService.getEvolutionChain(id)
////            }
////        }
//// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
//
//    suspend fun getPokemonColor(name: String): Result<PokemonColor> =
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
        runCatching {
            pokeApiService.getPokemonColor(name)
        }

    suspend fun getMoveDetails(name: String): Result<MoveDetail> =
        runCatching {
            moveCache.getOrPut(name) {
                pokeApiService.getMoveDetails(name)
            }
        }

    suspend fun getAbilityDetails(name: String): Result<AbilityDetail> =
        runCatching {
            abilityCache.getOrPut(name) {
                pokeApiService.getAbilityDetails(name)
            }
        }
} 