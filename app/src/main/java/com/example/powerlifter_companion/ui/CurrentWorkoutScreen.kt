package com.example.powerlifter_companion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.data.ExerciseLogInput
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.ExerciseLogStatus
import com.example.powerlifter_companion.entities.PostWorkout
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed

private data class ExerciseEntryState(
    val status: ExerciseLogStatus = ExerciseLogStatus.COMPLETED_AS_PLANNED,
    val actualReps: String = "",
    val actualWeight: String = "",
    val actualRpe: String = ""
)

/**
 * Today's Workout completion flow: for each planned exercise the user marks
 * it As Planned / Modified / Skipped. Modified reveals editable actual
 * reps/weight/RPE (exercise-level, no per-set logging per the requirements).
 * "Complete Workout" builds one ExerciseLogInput per exercise and hands the
 * whole list back to the caller to persist.
 */
@Composable
fun CurrentWorkoutScreen(
    workoutName: String,
    exercises: List<Exercise>,
    exerciseDefinitions: List<ExerciseDefinition>,
    existingPostWorkout: PostWorkout?,
    onCompleteWorkout: (List<ExerciseLogInput>) -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
    )

    // Starts in summary mode when a log already exists; "Edit Log" switches
    // to the entry form. Re-keyed on the workout's log so re-entering an
    // already-logged workout shows the summary again by default.
    var isEditing by remember(existingPostWorkout == null) { mutableStateOf(existingPostWorkout == null) }
    val entryStates = remember { mutableStateMapOf<Int, ExerciseEntryState>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = workoutName,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (existingPostWorkout != null && !isEditing) {
            CompletedBanner(
                postWorkout = existingPostWorkout,
                onEditClick = { isEditing = true }
            )
            return@Column
        }

        if (existingPostWorkout != null) {
            Text(
                text = "Editing existing log — completing again replaces it",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        exercises.forEach { exercise ->
            val exerciseName = exerciseDefinitions
                .firstOrNull { it.exerciseDefinitionID == exercise.exerciseDefinition }
                ?.name ?: "Unknown Exercise"

            val entry = entryStates[exercise.exerciseID] ?: ExerciseEntryState()

            CurrentWorkoutExerciseCard(
                exerciseName = exerciseName,
                plannedSets = exercise.sets?.toString() ?: "-",
                plannedReps = exercise.reps.toString(),
                plannedWeight = exercise.weight?.toString() ?: "-",
                plannedRpe = exercise.rpe?.toString() ?: "-",
                entry = entry,
                onEntryChange = { updated -> entryStates[exercise.exerciseID] = updated }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val logs = exercises.map { exercise ->
                    val entry = entryStates[exercise.exerciseID] ?: ExerciseEntryState()
                    val isModified = entry.status == ExerciseLogStatus.MODIFIED

                    ExerciseLogInput(
                        exerciseId = exercise.exerciseID,
                        status = entry.status,
                        actualSets = exercise.sets,
                        actualReps = if (isModified) entry.actualReps.toIntOrNull() ?: exercise.reps
                            else exercise.reps,
                        actualWeight = if (isModified) entry.actualWeight.toIntOrNull() ?: exercise.weight
                            else exercise.weight,
                        actualRpe = if (isModified) entry.actualRpe.toFloatOrNull() ?: exercise.rpe
                            else exercise.rpe
                    )
                }
                onCompleteWorkout(logs)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryRed,
                contentColor = Color.White
            )
        ) {
            Text("Complete Workout")
        }
    }
}

@Composable
private fun CompletedBanner(
    postWorkout: PostWorkout,
    onEditClick: () -> Unit
) {
    val accentColor = if (postWorkout.completedAsPlanned) Color(0xFF4CAF50) else Color(0xFFFFA726)
    val label = if (postWorkout.completedAsPlanned) "Completed as Planned" else "Completed (Modified)"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151515).copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onEditClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Log")
            }
        }
    }
}

@Composable
private fun CurrentWorkoutExerciseCard(
    exerciseName: String,
    plannedSets: String,
    plannedReps: String,
    plannedWeight: String,
    plannedRpe: String,
    entry: ExerciseEntryState,
    onEntryChange: (ExerciseEntryState) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151515).copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = exerciseName,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Planned: $plannedSets sets x $plannedReps reps  •  $plannedWeight  •  RPE $plannedRpe",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusChip(
                    label = "As Planned",
                    selected = entry.status == ExerciseLogStatus.COMPLETED_AS_PLANNED,
                    onClick = { onEntryChange(entry.copy(status = ExerciseLogStatus.COMPLETED_AS_PLANNED)) },
                    modifier = Modifier.weight(1f)
                )
                StatusChip(
                    label = "Modified",
                    selected = entry.status == ExerciseLogStatus.MODIFIED,
                    onClick = { onEntryChange(entry.copy(status = ExerciseLogStatus.MODIFIED)) },
                    modifier = Modifier.weight(1f)
                )
                StatusChip(
                    label = "Skipped",
                    selected = entry.status == ExerciseLogStatus.SKIPPED,
                    onClick = { onEntryChange(entry.copy(status = ExerciseLogStatus.SKIPPED)) },
                    modifier = Modifier.weight(1f)
                )
            }

            if (entry.status == ExerciseLogStatus.MODIFIED) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = entry.actualReps,
                        onValueChange = { onEntryChange(entry.copy(actualReps = it)) },
                        modifier = Modifier.weight(1f),
                        label = { Text("Reps") },
                        placeholder = { Text(plannedReps) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = entry.actualWeight,
                        onValueChange = { onEntryChange(entry.copy(actualWeight = it)) },
                        modifier = Modifier.weight(1f),
                        label = { Text("Weight") },
                        placeholder = { Text(plannedWeight) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = entry.actualRpe,
                        onValueChange = { onEntryChange(entry.copy(actualRpe = it)) },
                        modifier = Modifier.weight(1f),
                        label = { Text("RPE") },
                        placeholder = { Text(plannedRpe) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = if (selected) PrimaryRed else Color.White.copy(alpha = 0.10f)
    ) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
