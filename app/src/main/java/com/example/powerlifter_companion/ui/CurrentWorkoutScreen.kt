package com.example.powerlifter_companion.ui

import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed
import com.example.powerlifter_companion.ui.Components.WorkoutDetailSection
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed
import com.example.powerlifter_companion.viewmodel.TrainingViewModel
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Workout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun CurrentWorkoutScreen(
    workoutName: String,
    exercises: List<Exercise>,
    exerciseDefinitions: List<ExerciseDefinition>,
    onCompleteWorkout: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
    ) {
        Text(
            text = workoutName,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        exercises.forEach { exercise ->
            val exerciseName = exerciseDefinitions
                .firstOrNull {it.exerciseDefinitionID == exercise.exerciseDefinition}
                ?.name ?: "Unknown Exercise"

            CurrentWorkoutExerciseCard(
                exerciseName = exerciseName,
                sets = exercise.sets?.toString() ?: "-",
                reps = exercise.reps.toString(),
                weight = exercise.weight?.toString() ?: "-",
                rpe = exercise.rpe?.toString() ?: "-"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onCompleteWorkout,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryRed,
                contentColor = Color.White
            )
        ) {
            Text("Mark Workout Complete")
        }
    }
}

@Composable
fun CurrentWorkoutExerciseCard(
    exerciseName: String,
    sets: String,
    reps: String,
    weight: String,
    rpe: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151515).copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = exerciseName,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$sets sets x $reps reps",
                color = Color.White.copy(alpha = 0.85f)
            )

            Text(
                text = "Weight: $weight   RPE: $rpe",
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}