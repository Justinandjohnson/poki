package com.example.myapplication.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.viewmodel.PokemonViewModel
import java.util.Locale

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val pokemon = viewModel.pokemonDetails.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    LaunchedEffect(pokemonName) {
        viewModel.fetchPokemonDetails(pokemonName)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (pokemon != null) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    // Pokemon Image and Name
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
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
                                text = pokemon.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                },
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    // Types
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Types",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                pokemon.types.forEach { type ->
                                    TypeChip(type.type.name)
                                }
                            }
                        }
                    }

                    // Stats
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Stats",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            pokemon.stats.forEach { stat ->
                                StatBar(
                                    statName = stat.stat.name.replace("-", " ")
                                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                                    statValue = stat.base_stat
                                )
                            }
                        }
                    }

                    // Abilities
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Abilities",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            pokemon.abilities.forEach { ability ->
                                Text(
                                    text = "• ${ability.ability.name.replace("-", " ").capitalize(Locale.ROOT)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        error?.let {
            ErrorDialog(
                message = it,
                onDismiss = viewModel::clearError,
                onRetry = { viewModel.fetchPokemonDetails(pokemonName) }
            )
        }

        if (isLoading) {
            LoadingOverlay()
        }
    }
} 