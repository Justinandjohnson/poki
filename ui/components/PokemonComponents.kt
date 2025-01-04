package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.model.*

@Composable
fun PokemonBasicInfo(pokemon: PokemonDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
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
                text = pokemon.name.capitalize(),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun PokemonStats(stats: List<StatResponse>) {
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
            stats.forEach { stat ->
                StatBar(
                    statName = stat.stat.name.replace("-", " ").capitalize(),
                    statValue = stat.base_stat
                )
            }
        }
    }
}

@Composable
fun PokemonTypes(types: List<TypeResponse>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Types",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                types.forEach { type ->
                    TypeChip(type.type.name)
                }
            }
        }
    }
}

@Composable
private fun StatBar(statName: String, statValue: Int) {
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
            Text(text = statValue.toString(), style = MaterialTheme.typography.bodyMedium)
        }
        LinearProgressIndicator(
            progress = statValue / 255f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
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
            text = type.capitalize(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = Color.White
        )
    }
}

private fun getTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "normal" -> Color(0xFFA8A878)
        "fire" -> Color(0xFFF08030)
        "water" -> Color(0xFF6890F0)
        "electric" -> Color(0xFFF8D030)
        "grass" -> Color(0xFF78C850)
        "ice" -> Color(0xFF98D8D8)
        "fighting" -> Color(0xFFC03028)
        "poison" -> Color(0xFFA040A0)
        "ground" -> Color(0xFFE0C068)
        "flying" -> Color(0xFFA890F0)
        "psychic" -> Color(0xFFF85888)
        "bug" -> Color(0xFFA8B820)
        "rock" -> Color(0xFFB8A038)
        "ghost" -> Color(0xFF705898)
        "dragon" -> Color(0xFF7038F8)
        "dark" -> Color(0xFF705848)
        "steel" -> Color(0xFFB8B8D0)
        "fairy" -> Color(0xFFEE99AC)
        else -> Color.Gray
    }
} 