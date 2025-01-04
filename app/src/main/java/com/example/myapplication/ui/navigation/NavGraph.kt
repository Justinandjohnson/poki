package com.example.myapplication.ui.navigation

import com.example.myapplication.ui.screens.detail.PokemonDetailScreen
import com.example.myapplication.ui.screens.list.PokemonListScreen

sealed class Screen(val route: String) {
    object PokemonList : Screen("pokemon_list")
    object PokemonDetail : Screen("pokemon_detail/{name}") {
        fun createRoute(name: String) = "pokemon_detail/$name"
    }
}

@Composable
fun PokemonNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.PokemonList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.PokemonList.route) {
            PokemonListScreen(
                onPokemonClick = { pokemon ->
                    navController.navigate(
                        Screen.PokemonDetail.createRoute(pokemon.name)
                    )
                }
            )
        }

        composable(
            route = Screen.PokemonDetail.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("name")
                ?: return@composable
            
            PokemonDetailScreen(
                pokemonName = pokemonName,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
} 