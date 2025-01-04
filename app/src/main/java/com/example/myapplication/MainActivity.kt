package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.detail.PokemonDetailScreen
import com.example.myapplication.ui.screens.list.PokemonListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    PokemonApp()
                }
            }
        }
    }
}

@Composable
fun PokemonApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "pokemon_list"
    ) {
        composable("pokemon_list") {
            PokemonListScreen(
                onPokemonClick = { pokemon ->
                    navController.navigate("pokemon_detail/${pokemon.name}")
                }
            )
        }

        composable(
            route = "pokemon_detail/{name}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("name") ?: return@composable
            PokemonDetailScreen(pokemonName = pokemonName)
        }
    }
}