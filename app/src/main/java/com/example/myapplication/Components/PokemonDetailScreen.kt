package com.example.myapplication.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.*
import com.example.myapplication.getStatColor
import com.example.myapplication.getTypeColor
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Locale

@Composable
fun PokemonBasicInfo(pokemon: PokemonDetail) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.sprites.front_default)
                .crossfade(true)
                .build(),
            contentDescription = pokemon.name,
            modifier = Modifier.size(200.dp)
        )
        
        Text(
            text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pokemon.types.forEach { type ->
                TypeChip(type.type.name)
            }
        }
    }
}

@Composable
fun TypeChip(type: String) {
    Surface(
        color = getTypeColor(type),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = Color.White
        )
    }
}

@Composable
fun PokemonStats(stats: List<StatResponse>) {
    Column(modifier = Modifier.padding(16.dp)) {
        stats.forEach { stat ->
            EnhancedStatBar(
                statName = stat.stat.name.replace("-", " ")
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                baseStat = stat.base_stat
            )
        }
    }
}

@Composable
fun PokemonTypes(types: List<TypeResponse>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        types.forEach { type ->
            TypeChip(type.type.name)
        }
    }
}

@Composable
fun PokemonEvolutionChain(
    chain: ChainLink,
    onPokemonClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Evolution Chain",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        EvolutionItem(chain, onPokemonClick)
    }
}

@Composable
private fun EvolutionItem(
    chain: ChainLink,
    onPokemonClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = chain.species.name.capitalize(java.util.Locale.ROOT),
            modifier = Modifier.clickable { onPokemonClick(chain.species.name) }
        )
        chain.evolves_to.forEach { evolution ->
            Text("↓")
            EvolutionItem(evolution, onPokemonClick)
        }
    }
}

data class MoveGroup(
    val method: String,
    val moves: List<MoveResponse>
)

@Composable
fun PokemonDetailScreen(
    pokemonDetail: PokemonDetail?,
    species: PokemonSpecies?,
    evolutionChain: EvolutionChain?,
    selectedMove: MoveDetail?,
    selectedAbility: AbilityDetail?,
    isLoading: Boolean,
    onEvolutionClick: (String) -> Unit,
    onMoveClick: (String) -> Unit,
    onAbilityClick: (String) -> Unit
) {
    var selectedMoveGroup by remember { mutableStateOf("all") }
    val moveGroups = remember(pokemonDetail) {
        pokemonDetail?.let { detail ->
            val groupedMoves: Map<String, List<MoveResponse>> = detail.moves.groupBy { moveResponse ->
                moveResponse.version_group_details.firstOrNull()?.move_learn_method?.name ?: "unknown"
            }
            groupedMoves.entries.map { entry ->
                MoveGroup(entry.key, entry.value)
            }
        } ?: emptyList()
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            if (pokemonDetail != null) {
                PokemonBasicInfo(pokemonDetail)

                // Moves Section with Grouping
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Moves",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Move group filter chips
                        ScrollableChipGroup(
                            items = moveGroups.map { it.method },
                            selectedItem = selectedMoveGroup,
                            onSelectedChanged = { selectedMoveGroup = it }
                        )

                        LazyColumn(
                            modifier = Modifier.heightIn(max = 300.dp)
                        ) {
                            val filteredMoves = if (selectedMoveGroup == "all") {
                                pokemonDetail.moves ?: emptyList()
                            } else {
                                moveGroups.find { group -> group.method == selectedMoveGroup }?.moves ?: emptyList()
                            }

                            items(
                                items = filteredMoves,
                                key = { moveResponse: MoveResponse -> moveResponse.move.name }
                            ) { moveResponse: MoveResponse ->
                                EnhancedMoveItem(
                                    move = moveResponse,
                                    selectedMove = selectedMove,
                                    onClick = { onMoveClick(moveResponse.move.name) }
                                )
                            }
                        }
                    }
                }

                // Enhanced Stats Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Stats",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        pokemonDetail.stats.forEach { stat ->
                            EnhancedStatBar(
                                statName = stat.stat.name.replace("-", " ")
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                                baseStat = stat.base_stat
                            )
                        }
                    }
                }

                // Enhanced Abilities Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Abilities",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        pokemonDetail.abilities.forEach { abilityResponse ->
                            EnhancedAbilityItem(
                                ability = abilityResponse,
                                selectedAbility = selectedAbility,
                                onClick = { onAbilityClick(abilityResponse.ability.name) }
                            )
                        }
                    }
                }

                species?.let { speciesData ->
                    Text(
                        text = speciesData.genera.firstOrNull { it.language.name == "en" }?.genus ?: "",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = speciesData.flavor_text_entries
                            .firstOrNull { it.language.name == "en" }
                            ?.flavor_text ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                evolutionChain?.let { chain ->
                    PokemonEvolutionChain(
                        chain = chain.chain,
                        onPokemonClick = onEvolutionClick
                    )
                }
            }
        }
    }
}

@Composable
fun ScrollableChipGroup(
    items: List<String>,
    selectedItem: String,
    onSelectedChanged: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedItem == "all",
                onClick = { onSelectedChanged("all") },
                label = { Text("All") }
            )
        }
        items(
            items = items,
            key = { item: String -> item }
        ) { item ->
            FilterChip(
                selected = selectedItem == item,
                onClick = { onSelectedChanged(item) },
                label = { Text(item.replace("-", " ")
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }
            )
        }
    }
}

@Composable
fun EnhancedMoveItem(
    move: MoveResponse,
    selectedMove: MoveDetail?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            selectedMove?.let { moveDetail ->
                Text(moveDetail.damage_class.getIcon())
                TypeChip(moveDetail.type.name)
            }
            Text(
                text = move.move.name.replace("-", " ")
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (selectedMove?.name == move.move.name) {
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    selectedMove.power?.let { power ->
                        Text("Power: $power", style = MaterialTheme.typography.bodySmall)
                    }
                    selectedMove.accuracy?.let { accuracy ->
                        Text("Accuracy: $accuracy%", style = MaterialTheme.typography.bodySmall)
                    }
                    Text("PP: ${selectedMove.pp}", style = MaterialTheme.typography.bodySmall)
                }
                selectedMove.meta?.let { meta ->
                    if (meta.healing > 0) {
                        Text("Healing: ${meta.healing}%", style = MaterialTheme.typography.bodySmall)
                    }
                    if (meta.stat_chance > 0) {
                        Text("Effect Chance: ${meta.stat_chance}%", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Text(
                    text = selectedMove.effect_entries
                        .firstOrNull { it.language.name == "en" }
                        ?.short_effect ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun EnhancedAbilityItem(
    ability: AbilityResponse,
    selectedAbility: AbilityDetail?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (ability.is_hidden)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (ability.is_hidden) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Hidden Ability",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = ability.ability.name.replace("-", " ").capitalize(Locale.ROOT),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (selectedAbility?.name == ability.ability.name) {
                Column(modifier = Modifier.padding(start = 32.dp, top = 8.dp)) {
                    selectedAbility.effect_entries
                        .firstOrNull { it.language.name == "en" }
                        ?.let { effect ->
                            Text(
                                text = effect.effect,
                                style = MaterialTheme.typography.bodySmall
                            )
                            if (selectedAbility.effect_changes.isNotEmpty()) {
                                Text(
                                    text = "Effect changes:",
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                selectedAbility.effect_changes.forEach { change ->
                                    Text(
                                        text = "• ${change.effect_entries.firstOrNull { it.language.name == "en" }?.effect ?: ""}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun EnhancedStatBar(
    statName: String,
    baseStat: Int
) {
    val maxStat = remember(baseStat) {
        calculateMaxStat(baseStat)
    }
    val minStat = remember(baseStat) {
        calculateMinStat(baseStat)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = statName, style = MaterialTheme.typography.bodyMedium)
            Text(text = baseStat.toString(), style = MaterialTheme.typography.bodyMedium)
        }

        Box(modifier = Modifier.height(24.dp)) {
            // Background bar
            LinearProgressIndicator(
                progress = 1f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            // Min-Max range bar
            LinearProgressIndicator(
                progress = maxStat / 255f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .align(Alignment.Center),
                color = getStatColor(baseStat).copy(alpha = 0.3f)
            )

            // Base stat bar
            LinearProgressIndicator(
                progress = baseStat / 255f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .align(Alignment.Center),
                color = getStatColor(baseStat)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Min: $minStat",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Max: $maxStat",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun calculateMaxStat(baseStat: Int): Int {
    return ((2 * baseStat + 31 + 252/4) * 100/100 + 5) * 1.1.toInt()
}

private fun calculateMinStat(baseStat: Int): Int {
    return ((2 * baseStat + 0 + 0/4) * 100/100 + 5) * 0.9.toInt()
}

