package com.example.myapplication.domain.usecase

import javax.inject.Inject
import com.example.myapplication.data.repository.PokemonRepository
import com.example.myapplication.domain.model.AbilityDomain

// --Commented out by Inspection START (1/4/25, 2:11 PM):
//class GetAbilityDetailUseCase @Inject constructor(
//    private val repository: PokemonRepository
//) {
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
    suspend operator fun invoke(name: String): Result<AbilityDomain> =
        repository.getAbilityDetails(name).map { ability ->
            AbilityDomain(
                name = ability.name,
                description = ability.effect_entries
                    .firstOrNull { it.language.name == "en" }
                    ?.effect ?: "",
                isHidden = false
            )
        }
} 