package com.example.myapplication.ui.screens.detail.components

import java.util.Locale

@Composable
fun AbilitiesCard(
    abilities: List<AbilityDomain>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
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
            
            abilities.forEach { ability ->
                AbilityItem(ability = ability)
            }
        }
    }
}

@Composable
private fun AbilityItem(
    ability: AbilityDomain
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (ability.isHidden)
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
                if (ability.isHidden) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Hidden Ability",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = ability.name.capitalize(Locale.ROOT),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Text(
                text = ability.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 32.dp, top = 4.dp)
            )
        }
    }
} 