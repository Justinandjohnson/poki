package com.example.myapplication.ui.screens.detail.components

import com.example.myapplication.domain.model.StatDomain

@Composable
fun PokemonStats(
    stats: List<StatDomain>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Stats",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            stats.forEach { stat ->
                EnhancedStatBar(
                    statName = stat.name,
                    baseStat = stat.value,
                    maxStat = stat.maxValue
                )
            }
        }
    }
} 