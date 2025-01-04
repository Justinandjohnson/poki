package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.myapplication.data.api.PokeApiService
import com.example.myapplication.data.model.*
import com.example.myapplication.data.repository.PokemonRepository
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.text.intl.Locale
import com.example.myapplication.Components.PokemonDetailScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color

// View Model
class PokemonViewModel : ViewModel() {
    private val _pokemonList = mutableStateOf<List<Pokemon>>(emptyList())
    val pokemonList: State<List<Pokemon>> = _pokemonList

    private val _pokemonDetails = mutableStateOf<PokemonDetail?>(null)
    val pokemonDetails: State<PokemonDetail?> = _pokemonDetails

    private val _selectedMove = mutableStateOf<MoveDetail?>(null)
    val selectedMove: State<MoveDetail?> = _selectedMove

    private val _selectedAbility = mutableStateOf<AbilityDetail?>(null)
    val selectedAbility: State<AbilityDetail?> = _selectedAbility

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    // Update API service
    private val apiService = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokeApiService::class.java)

    private val repository = PokemonRepository(apiService)

    fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val response = repository.getPokemonList(151).getOrThrow()
                _pokemonList.value = response.results
            } catch (e: Exception) {
                _error.value = "Failed to load Pokemon list: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchPokemonDetails(name: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val pokemon = repository.getPokemonDetails(name).getOrThrow()
                _pokemonDetails.value = pokemon
            } catch (e: Exception) {
                _error.value = "Failed to load Pokemon details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchMoveDetails(name: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                val move = repository.getMoveDetails(name).getOrThrow()
                _selectedMove.value = move
            } catch (e: Exception) {
                _error.value = "Failed to load move details: ${e.message}"
            }
        }
    }

    fun fetchAbilityDetails(name: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                val ability = repository.getAbilityDetails(name).getOrThrow()
                _selectedAbility.value = ability
            } catch (e: Exception) {
                _error.value = "Failed to load ability details: ${e.message}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

// UI Components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    pokemonList: List<Pokemon>,
    isLoading: Boolean,
    error: String?,
    onPokemonClick: (Pokemon) -> Unit,
    onErrorDismiss: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pokemonList) { pokemon ->
                PokemonListItem(pokemon = pokemon, onPokemonClick = onPokemonClick)
            }
        }

        error?.let {
            ErrorDialog(
                message = it,
                onDismiss = onErrorDismiss,
                onRetry = onRetry
            )
        }

        if (isLoading) {
            LoadingOverlay()
        }
    }
}

@Composable
fun PokemonListItem(pokemon: Pokemon, onPokemonClick: (Pokemon) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPokemonClick(pokemon) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${getPokemonId(pokemon.url)}.png")
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = pokemon.name.capitalize(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

// Add this helper function to extract Pokemon ID from URL
private fun getPokemonId(url: String): String {
    return url.split("/").dropLast(1).last()
}

@Composable
fun PokemonDetailsScreen(pokemon: PokemonDetail?, modifier: Modifier = Modifier) {
    if (pokemon != null) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                // Pokemon Image and Name
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
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                // Types
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Types",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
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

                // Basic Info
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Basic Info",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        InfoRow("Height", "${pokemon.height / 10.0}m")
                        InfoRow("Weight", "${pokemon.weight / 10.0}kg")
                    }
                }

                // Stats
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Stats",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        pokemon.stats.forEach { stat ->
                            StatBar(
                                statName = stat.stat.name.replace("-", " ").capitalize(),
                                statValue = stat.base_stat
                            )
                        }
                    }
                }

                // Abilities
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Abilities",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        pokemon.abilities.forEach { ability ->
                            Text(
                                text = "• ${ability.ability.name.replace("-", " ").capitalize()}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
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
            text = type.capitalize(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            color = Color.White
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun StatBar(statName: String, statValue: Int) {
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
                .height(8.dp),
            color = getStatColor(statValue)
        )
    }
}

fun getTypeColor(type: String): Color {
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

fun getStatColor(value: Int): Color {
    return when {
        value >= 150 -> Color(0xFF4CAF50) // High stats
        value >= 90 -> Color(0xFF8BC34A)  // Good stats
        value >= 50 -> Color(0xFFFFC107)  // Average stats
        else -> Color(0xFFFF5722)         // Low stats
    }
}

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Error") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                onRetry()
            }) {
                Text("Retry")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PokemonApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PokemonApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "pokemonList",
        modifier = modifier
    ) {
        composable("pokemonList") {
            val viewModel: PokemonViewModel = viewModel()
            val pokemonList = viewModel.pokemonList.value
            val isLoading = viewModel.isLoading.value
            val error = viewModel.error.value

            LaunchedEffect(Unit) {
                viewModel.fetchPokemonList()
            }

            PokemonListScreen(
                pokemonList = pokemonList,
                isLoading = isLoading,
                error = error,
                onPokemonClick = { pokemon ->
                    navController.navigate("pokemonDetails/${pokemon.name}")
                },
                onErrorDismiss = viewModel::clearError,
                onRetry = viewModel::fetchPokemonList,
                modifier = modifier
            )
        }
        composable(
            "pokemonDetails/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName")
            val viewModel: PokemonViewModel = viewModel()
            val pokemonDetails = viewModel.pokemonDetails.value
            val selectedMove = viewModel.selectedMove.value
            val selectedAbility = viewModel.selectedAbility.value
            val isLoading = viewModel.isLoading.value
            val error = viewModel.error.value

            LaunchedEffect(pokemonName) {
                if (pokemonName != null) {
                    viewModel.fetchPokemonDetails(pokemonName)
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                PokemonDetailScreen(
                    pokemonDetail = pokemonDetails,
                    species = null,
                    evolutionChain = null,
                    selectedMove = selectedMove,
                    selectedAbility = selectedAbility,
                    isLoading = isLoading,
                    onBackClick = { navController.navigateUp() },
                    onEvolutionClick = { /* Handle evolution click */ },
                    onMoveClick = { moveName -> viewModel.fetchMoveDetails(moveName) },
                    onAbilityClick = { abilityName -> viewModel.fetchAbilityDetails(abilityName) }
                )

                error?.let {
                    ErrorDialog(
                        message = it,
                        onDismiss = viewModel::clearError,
                        onRetry = {
                            pokemonName?.let { name ->
                                viewModel.fetchPokemonDetails(name)
                            }
                        }
                    )
                }

                if (isLoading) {
                    LoadingOverlay()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonAppPreview() {
    MyApplicationTheme {
        PokemonApp()
    }
}