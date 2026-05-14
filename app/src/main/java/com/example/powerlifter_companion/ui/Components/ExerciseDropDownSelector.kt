package com.example.powerlifter_companion.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.MuscleGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.ui.theme.PrimaryRed

@Composable
fun ExerciseDropdownSelector(
    exerciseDefinitions: List<ExerciseDefinition>,
    selectedExerciseId: Long?,
    selectedCategories: Set<ExerciseCategory>,
    selectedMuscleGroups: Set<MuscleGroup>,
    onCategoriesChange: (Set<ExerciseCategory>) -> Unit,
    onMuscleGroupsChange: (Set<MuscleGroup>) -> Unit,
    onExerciseSelected: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var filterExpanded by remember { mutableStateOf(false) }

    val filteredExercises = exerciseDefinitions
        .filter {
            selectedCategories.isEmpty() || it.category in selectedCategories
        }
        .filter {
            selectedMuscleGroups.isEmpty() || it.muscleGroup in selectedMuscleGroups
        }
        .sortedBy { it.name }

    val selectedExerciseName =
        exerciseDefinitions.firstOrNull {
            it.exerciseDefinitionID.toLong() == selectedExerciseId
        }?.name ?: "Select Exercise"

    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                IconButton(
                    onClick = { filterExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter Exercises",
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = filterExpanded,
                    onDismissRequest = { filterExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Clear Filters") },
                        onClick = {
                            onCategoriesChange(emptySet())
                            onMuscleGroupsChange(emptySet())
                        }
                    )

                    ExerciseCategory.values().forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (category in selectedCategories)
                                        "✓ Type: ${category.name}"
                                    else
                                        "Type: ${category.name}"
                                )
                            },
                            onClick = {
                                onCategoriesChange(
                                    if (category in selectedCategories) {
                                        selectedCategories - category
                                    } else {
                                        selectedCategories + category
                                    }
                                )
                            }
                        )
                    }

                    MuscleGroup.values().forEach { muscleGroup ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (muscleGroup in selectedMuscleGroups)
                                        "✓ Muscle: ${muscleGroup.name}"
                                    else
                                        "Muscle: ${muscleGroup.name}"
                                )
                            },
                            onClick = {
                                onMuscleGroupsChange(
                                    if (muscleGroup in selectedMuscleGroups) {
                                        selectedMuscleGroups - muscleGroup
                                    } else {
                                        selectedMuscleGroups + muscleGroup
                                    }
                                )
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRed,
                    contentColor = Color.White
                )
            ) {
                Text(selectedExerciseName)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredExercises.forEach { exercise ->
                DropdownMenuItem(
                    text = { Text(exercise.name) },
                    onClick = {
                        onExerciseSelected(exercise.exerciseDefinitionID.toLong())
                        expanded = false
                    }
                )
            }
        }
    }
}