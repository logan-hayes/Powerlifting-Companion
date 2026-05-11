package com.example.powerlifter_companion.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed
import com.example.powerlifter_companion.viewmodel.ExerciseViewModel
import androidx.compose.runtime.LaunchedEffect
@Composable
fun ExerciseListScreen(
    exerciseViewModel: ExerciseViewModel
) {
    LaunchedEffect(Unit) {
        exerciseViewModel.seedExercises()
    }
    val exercises by exerciseViewModel.exerciseDefinitions.collectAsState(initial = emptyList())

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(16.dp)
    ) {
        Text(
            text = "Exercise List",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(exercises) { exercise ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = exercise.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Category: ${exercise.category}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Muscle Group: ${exercise.muscleGroup}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}