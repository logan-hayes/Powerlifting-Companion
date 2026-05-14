package com.example.powerlifter_companion.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.MuscleGroup
import com.example.powerlifter_companion.ui.BlockTableHeader
import com.example.powerlifter_companion.ui.BlockTableRow
import com.example.powerlifter_companion.ui.Components.ExerciseDropdownSelector
import com.example.powerlifter_companion.ui.theme.PrimaryRed

@Composable
fun WorkoutDetailSection(
    workoutName: String,
    exercises: List<Exercise>,
    exerciseDefinitions: List<ExerciseDefinition>,
    selectedExerciseId: Long?,
    sets: Int?,
    reps: Int?,
    weight: Int?,
    rpe: Float?,
    notes: String,
    isAddingExercise: Boolean,
    onBackClick: () -> Unit,
    onExerciseSelected: (Long) -> Unit,
    onSetsChange: (Int?) -> Unit,
    onRepsChange: (Int?) -> Unit,
    onWeightChange: (Int?) -> Unit,
    onRpeChange: (Float?) -> Unit,
    onNotesChange: (String) -> Unit,
    onStartAddExercise: () -> Unit,
    onCancelAddExercise: () -> Unit,
    onAddExercise: () -> Unit,
    onDeleteExercise: (Exercise) -> Unit,
    onStartWorkout: () -> Unit,
) {
    var showDeleteExerciseDialog by remember { mutableStateOf(false) }
    var selectedCategories by remember { mutableStateOf(setOf<ExerciseCategory>()) }
    var selectedMuscleGroups by remember { mutableStateOf(setOf<MuscleGroup>()) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                tint = Color.White
            )
        }

        Text(
            text = "",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = workoutName,
        color = Color.White,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = onStartWorkout,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            contentColor = Color.White
        )
    ) {
        Text("Start Workout")
    }

    Spacer(modifier = Modifier.height(16.dp))

    BlockTableHeader()

    Spacer(modifier = Modifier.height(8.dp))

    exercises.forEach { exercise ->
        BlockTableRow(
            exercise = exerciseDefinitions
                .firstOrNull { it.exerciseDefinitionID == exercise.exerciseDefinition }
                ?.name ?: "Unknown",
            sets = exercise.sets?.toString() ?: "",
            reps = exercise.reps.toString(),
            weight = exercise.weight?.toString() ?: "",
            rpe = exercise.rpe?.toString() ?: "",
            notes = exercise.notes ?: ""
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (!isAddingExercise) {
        Button(
            onClick = onStartAddExercise,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryRed,
                contentColor = Color.White
            )
        ) {
            Text("+ Add Exercise")
        }

        if (exercises.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDeleteExerciseDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                )
            ) {
                Text("Delete Exercise")
            }
        }
    }

    if (isAddingExercise) {
        Text(
            text = "Add Exercise to $workoutName",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        ExerciseDropdownSelector(
            exerciseDefinitions = exerciseDefinitions,
            selectedExerciseId = selectedExerciseId,
            selectedCategories = selectedCategories,
            selectedMuscleGroups = selectedMuscleGroups,
            onCategoriesChange = { selectedCategories = it },
            onMuscleGroupsChange = { selectedMuscleGroups = it },
            onExerciseSelected = onExerciseSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = sets?.toString() ?: "",
            onValueChange = { onSetsChange(it.toIntOrNull()) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Sets") },
            singleLine = true
        )

        OutlinedTextField(
            value = reps?.toString() ?: "",
            onValueChange = { onRepsChange(it.toIntOrNull()) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Reps") },
            singleLine = true
        )

        OutlinedTextField(
            value = weight?.toString() ?: "",
            onValueChange = { onWeightChange(it.toIntOrNull()) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Weight") },
            singleLine = true
        )

        OutlinedTextField(
            value = rpe?.toString() ?: "",
            onValueChange = { onRpeChange(it.toFloatOrNull()) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("RPE") },
            singleLine = true
        )

        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Notes") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onAddExercise,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryRed,
                contentColor = Color.White
            )
        ) {
            Text("Save Exercise")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onCancelAddExercise,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text("Cancel")
        }
    }

    if (showDeleteExerciseDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteExerciseDialog = false },
            title = { Text("Delete Exercise") },
            text = {
                Column {
                    exercises.forEach { exercise ->
                        val exerciseName = exerciseDefinitions
                            .firstOrNull { it.exerciseDefinitionID == exercise.exerciseDefinition }
                            ?.name ?: "Unknown"

                        Button(
                            onClick = {
                                onDeleteExercise(exercise)
                                showDeleteExerciseDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(exerciseName)
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = { showDeleteExerciseDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}