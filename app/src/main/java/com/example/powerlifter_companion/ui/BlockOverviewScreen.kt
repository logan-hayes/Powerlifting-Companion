package com.example.powerlifter_companion.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.Workout
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed
import com.example.powerlifter_companion.viewmodel.TrainingViewModel
import kotlinx.coroutines.launch

/**
 * Continuous-scroll block overview: replaces the block -> week -> workout
 * drill-down with one scrolling view of every week/workout in the block,
 * plus a week-chip strip that jumps the scroll position to a given week.
 */
@Composable
fun BlockOverviewScreen(
    blockName: String,
    overview: List<TrainingViewModel.WeekWithWorkouts>,
    exerciseDefinitions: List<ExerciseDefinition>,
    onBackClick: () -> Unit,
    onWorkoutClick: (Workout) -> Unit,
    onAddWorkoutClick: (weekId: Long, currentWorkoutCount: Int) -> Unit,
    onDeleteWorkout: (Workout) -> Unit
) {
    val gradient = Brush.verticalGradient(colors = listOf(BackgroundGray, PrimaryRed))
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = blockName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        if (overview.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.18f))
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                overview.forEachIndexed { index, weekWithWorkouts ->
                    WeekChip(
                        label = "Week ${weekWithWorkouts.week.weekNumber}",
                        onClick = {
                            scope.launch { listState.animateScrollToItem(index) }
                        }
                    )
                }
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(overview, key = { it.week.trainingWeekId }) { weekWithWorkouts ->
                WeekSection(
                    weekWithWorkouts = weekWithWorkouts,
                    exerciseDefinitions = exerciseDefinitions,
                    onWorkoutClick = onWorkoutClick,
                    onAddWorkoutClick = {
                        onAddWorkoutClick(weekWithWorkouts.week.trainingWeekId, weekWithWorkouts.workouts.size)
                    },
                    onDeleteWorkout = onDeleteWorkout
                )
            }
        }
    }
}

@Composable
private fun WeekChip(label: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = Color.White.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun WeekSection(
    weekWithWorkouts: TrainingViewModel.WeekWithWorkouts,
    exerciseDefinitions: List<ExerciseDefinition>,
    onWorkoutClick: (Workout) -> Unit,
    onAddWorkoutClick: () -> Unit,
    onDeleteWorkout: (Workout) -> Unit
) {
    Column {
        Text(
            text = "Week ${weekWithWorkouts.week.weekNumber}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAddWorkoutClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.10f),
                contentColor = Color.White
            )
        ) {
            Text("+ Add Workout to Week ${weekWithWorkouts.week.weekNumber}")
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (weekWithWorkouts.workouts.isEmpty()) {
            Text(
                text = "No workouts yet",
                color = Color.White.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            weekWithWorkouts.workouts.forEach { workoutWithExercises ->
                WorkoutTableCard(
                    workoutWithExercises = workoutWithExercises,
                    exerciseDefinitions = exerciseDefinitions,
                    onClick = { onWorkoutClick(workoutWithExercises.workout) },
                    onDeleteClick = { onDeleteWorkout(workoutWithExercises.workout) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun WorkoutTableCard(
    workoutWithExercises: TrainingViewModel.WorkoutWithExercises,
    exerciseDefinitions: List<ExerciseDefinition>,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth()) {
        // Thin left-border accent instead of full-row tinting
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(
                    PrimaryRed,
                    shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                )
        )
        Card(
            onClick = onClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 18.dp,
                bottomEnd = 18.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = workoutWithExercises.workout.workoutName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Workout Options"
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete Workout") },
                                onClick = {
                                    menuExpanded = false
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                ExerciseTableHeader()
                workoutWithExercises.exercises.forEach { exercise ->
                    val name = exerciseDefinitions
                        .firstOrNull { it.exerciseDefinitionID == exercise.exerciseDefinition }
                        ?.name ?: "Unknown"
                    ExerciseTableRow(exercise = exercise, exerciseName = name)
                }

                if (workoutWithExercises.exercises.isEmpty()) {
                    Text(
                        text = "No exercises added",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Workout?") },
            text = { Text("Are you sure you want to delete ${workoutWithExercises.workout.workoutName}?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryRed,
                        contentColor = Color.White
                    )
                ) { Text("Delete") }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun ExerciseTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        TableHeaderCell("Exercise", weight = 2f, align = TextAlign.Start)
        TableHeaderCell("Sets", weight = 1f, align = TextAlign.End)
        TableHeaderCell("Reps", weight = 1f, align = TextAlign.End)
        TableHeaderCell("Wt", weight = 1f, align = TextAlign.End)
        TableHeaderCell("RPE", weight = 1f, align = TextAlign.End)
    }
}

@Composable
private fun RowScope.TableHeaderCell(text: String, weight: Float, align: TextAlign) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        textAlign = align,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    )
}

@Composable
private fun ExerciseTableRow(exercise: Exercise, exerciseName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        TableCellText(exerciseName, weight = 2f, align = TextAlign.Start)
        TableCellText(exercise.sets?.toString() ?: "-", weight = 1f, align = TextAlign.End)
        TableCellText(exercise.reps.toString(), weight = 1f, align = TextAlign.End)
        TableCellText(
            exercise.weight?.toString()
                ?: exercise.maxPercentage?.let { "${it.toInt()}%" }
                ?: "-",
            weight = 1f,
            align = TextAlign.End
        )
        TableCellText(exercise.rpe?.toString() ?: "-", weight = 1f, align = TextAlign.End)
    }
}

@Composable
private fun RowScope.TableCellText(text: String, weight: Float, align: TextAlign) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = align,
        color = MaterialTheme.colorScheme.onSurface
    )
}
