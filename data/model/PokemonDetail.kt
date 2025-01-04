package com.example.myapplication.data.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<TypeResponse>,
    val height: Int,
    val weight: Int,
    val stats: List<StatResponse>,
    val abilities: List<AbilityResponse>,
    val species: Species,
    val moves: List<MoveResponse>
)

data class Species(
    val name: String,
    val url: String
)

data class TypeResponse(
    val type: Type
)

data class Type(
    val name: String
)

data class StatResponse(
    val base_stat: Int,
    val stat: Stat
)

data class Stat(
    val name: String
)

data class AbilityResponse(
    val ability: Ability,
    val is_hidden: Boolean,
    val slot: Int
)

data class Ability(
    val name: String,
    val url: String
)

data class MoveResponse(
    val move: Move,
    val version_group_details: List<VersionGroupDetail>
)

data class Move(
    val name: String,
    val url: String
)

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
)

data class MoveLearnMethod(
    val name: String,
    val url: String
)

data class VersionGroup(
    val name: String,
    val url: String
)

data class MoveDetail(
    val id: Int,
    val name: String,
    val accuracy: Int?,
    val power: Int?,
    val pp: Int,
    val priority: Int,
    val damage_class: DamageClass,
    val type: Type,
    val effect_entries: List<EffectEntry>,
    val meta: MoveMeta?
)

data class DamageClass(
    val name: String,
    val url: String
) {
    fun getIcon(): String = when(name.lowercase()) {
        "physical" -> "⚔️"
        "special" -> "✨"
        else -> "⭕"
    }
}

data class EffectEntry(
    val effect: String,
    val short_effect: String,
    val language: Language
)

data class Language(
    val name: String,
    val url: String
)

data class AbilityDetail(
    val id: Int,
    val name: String,
    val effect_entries: List<EffectEntry>,
    val is_main_series: Boolean,
    val generation: Generation,
    val effect_changes: List<AbilityEffectChange>
)

data class AbilityEffectChange(
    val effect_entries: List<EffectEntry>,
    val version_group: VersionGroup
)

data class Generation(
    val name: String,
    val url: String
)

data class MoveMeta(
    val ailment: MoveAilment,
    val category: MoveCategory,
    val healing: Int,
    val stat_chance: Int
)

data class MoveAilment(
    val name: String,
    val url: String
)

data class MoveCategory(
    val name: String,
    val url: String
) 