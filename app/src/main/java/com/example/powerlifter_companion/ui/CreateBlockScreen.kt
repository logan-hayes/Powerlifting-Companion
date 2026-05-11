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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.ui.Components.ExerciseListScreen
import com.example.powerlifter_companion.viewmodel.ExerciseViewModel


@Composable
fun CreateBlockUI(
    exerciseViewModel: ExerciseViewModel
) {
    val blockGradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
    )

    var blockName by remember { mutableStateOf("") }
    var selectedLength by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(blockGradient)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Create Training Block",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Set up a new training cycle",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E).copy(alpha = 0.92f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Block Name",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = blockName,
                    onValueChange = { blockName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Example: Meet Prep Block") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Block Length",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(4, 6, 8, 12).forEach { weeks ->
                        Button(
                            onClick = { selectedLength = weeks },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    if (selectedLength == weeks) PrimaryRed
                                    else Color.DarkGray
                            )
                        ) {
                            Text("$weeks")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = selectedLength?.let { "$it weeks selected" } ?: "No length selected",
                    color = Color.White.copy(alpha = 0.65f)
                )

                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryRed
                    )
                ) {
                    Text("Create Block")
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    ExerciseListScreen(exerciseViewModel)
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
