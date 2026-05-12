package com.example.powerlifter_companion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.ui.theme.PrimaryRed

@Composable
fun ExerciseSelector(
    exerciseDefinitions: List<ExerciseDefinition>,
    selectedExerciseId: Long?,
    onExerciseSelected: (Long) -> Unit
) {

    Column {

        Text(
            text = "Select Exercise",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.height(250.dp)
        ) {

            items(exerciseDefinitions) { exercise ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            onExerciseSelected(exercise.exerciseDefinitionID.toLong())
                        },
                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (selectedExerciseId == exercise.exerciseDefinitionID.toLong())
                                PrimaryRed
                            else
                                Color.DarkGray
                    )
                ) {

                    Text(
                        text = exercise.name,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}