package com.example.myapplication.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.data.model.Pokemon
import com.example.myapplication.ui.components.ErrorDialog
import com.example.myapplication.ui.components.LoadingOverlay
import com.example.myapplication.ui.components.PokemonListItem
import com.example.myapplication.ui.viewmodel.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val pokemonList = viewModel.pokemonList.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    Box(modifier = Modifier.fillMaxSize()) {
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
                onDismiss = viewModel::clearError,
                onRetry = viewModel::fetchPokemonList
            )
        }

        if (isLoading) {
            LoadingOverlay()
        }
    }
} 