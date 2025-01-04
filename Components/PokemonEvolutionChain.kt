package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.ChainLink

@Composable
fun PokemonEvolutionChain(
    chain: ChainLink,
    onPokemonClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(chain.species.name)
        
        chain.evolves_to.forEach { evolution ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("↓")
            Text(evolution.species.name)
            // Add evolution details like level, item required etc.
        }
    }
} 