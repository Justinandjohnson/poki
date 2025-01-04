package com.example.myapplication.ui.screens.detail.components

import com.example.myapplication.domain.model.MoveDomain
import java.util.Locale

@Composable
fun MovesCard(
    moves: List<MoveDomain>,
    selectedMove: MoveDomain?,
    onMoveClick: (MoveDomain) -> Unit,
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
                text = "Moves",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyColumn(
                modifier = Modifier.heightIn(max = 300.dp)
            ) {
                items(moves) { move ->
                    MoveItem(
                        move = move,
                        isSelected = selectedMove?.name == move.name,
                        onClick = { onMoveClick(move) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MoveItem(
    move: MoveDomain,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(move.damageClass.getIcon())
            TypeChip(move.type)
            Text(
                text = move.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (isSelected) {
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    move.power?.let { power ->
                        Text("Power: $power", style = MaterialTheme.typography.bodySmall)
                    }
                    move.accuracy?.let { accuracy ->
                        Text("Accuracy: $accuracy%", style = MaterialTheme.typography.bodySmall)
                    }
                    Text("PP: ${move.pp}", style = MaterialTheme.typography.bodySmall)
                }
                Text(
                    text = move.effect,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
} 