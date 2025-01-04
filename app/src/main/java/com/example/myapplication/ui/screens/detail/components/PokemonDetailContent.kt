package com.example.myapplication.ui.screens.detail.components

import com.example.myapplication.domain.model.MoveDomain
import com.example.myapplication.domain.model.PokemonDetailDomain

@Composable
fun PokemonDetailContent(
    pokemon: PokemonDetailDomain,
    selectedMove: MoveDomain?,
    onMoveClick: (MoveDomain) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            PokemonBasicInfo(
                imageUrl = pokemon.imageUrl,
                name = pokemon.name,
                types = pokemon.types
            )
        }

        item {
            PokemonStats(stats = pokemon.stats)
        }

        item {
            AbilitiesCard(abilities = pokemon.abilities)
        }

        item {
            MovesCard(
                moves = pokemon.moves,
                selectedMove = selectedMove,
                onMoveClick = onMoveClick
            )
        }
    }
} 