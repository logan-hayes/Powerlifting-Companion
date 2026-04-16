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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.viewmodel.ExerciseViewModel



@Composable
fun CreateBlockUI(
    exerciseViewModel: ExerciseViewModel)
{
    val blockGradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(blockGradient)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "Meet Prep Block",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Week 2 daye 1",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White.copy(alpha = 0.88f))

        Spacer(modifier = Modifier.height(20.dp))

        BlockTableHeader()

        Spacer(modifier = Modifier.height(10.dp))

        BlockTableRow(
            exercise = "SB Squats",
            sets = "3",
            reps = "7",
            weight = "N/A",
            rpe = "8",
            notes = "pause 1 second at the bottom"

        )

        BlockTableRow(
            exercise = "Bench Press",
            sets = "5",
            reps = "5",
            weight = "275",
            rpe = "N/A",
            notes = "pause 1 second at the bottom"

        )

        BlockTableRow(
            exercise = "Seated Good-Mornings",
            sets = "3",
            reps = "7",
            weight = "null",
            rpe = "8",
            notes = "pause 1 second at the bottom"

        )
    }
}

@Composable
fun BlockTableRow(
    exercise: String,
    sets: String,
    reps: String,
    weight: String,
    rpe: String,
    notes: String)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TableCell(exercise, weight = 2f)
        TableCell(sets, weight = 1f)
        TableCell(reps, weight = 1f)
        TableCell(weight, weight = 1f)
        TableCell(rpe, weight = 1f)
        TableCell(notes, weight = 2f)
    }
}
