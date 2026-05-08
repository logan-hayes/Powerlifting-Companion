package com.example.powerlifter_companion.ui

import android.R.attr.name
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed
import com.example.powerlifter_companion.viewmodel.TrainingViewModel


@Composable
fun blockUi(
    trainingViewModel: TrainingViewModel
) {
    val blockName by trainingViewModel.trainingBlockName.collectAsState()
    val blockLength by trainingViewModel.blockLength.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Select a block or create a new one",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                trainingViewModel.updateBlockName(blockName)
                trainingViewModel.updateBlockLength(blockLength)
                trainingViewModel.createBlock()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Create Training Block")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Blocks list
        BlockCard(
            blockName = "Hypertrophy",
            lengthWeeks = "6 weeks",
            startDate = "4/1/26"
        )

        BlockCard(
            blockName = "Strength",
            lengthWeeks = "4 weeks",
            startDate = "5/15/26"
        )

        BlockCard(
            blockName = "Peaking",
            lengthWeeks = "4 weeks",
            startDate = "7/1/26"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun blockHeader(){
    TopAppBar(
        title = {
            Text(
                text = "Training Blocks",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false)
{
    Box(
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart)
    {
        Text(
            text = text,
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
            color = if (isHeader) Color.Black else Color.White,
            style = if(isHeader)
                MaterialTheme.typography.bodyMedium
            else
                MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun BlockTableHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.88f))
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        TableCell("Exercise", weight = 2f, isHeader = true)
        TableCell("Sets", weight = 1f, isHeader = true)
        TableCell("Reps", weight = 1f, isHeader = true)
        TableCell("Weight", weight = 1f, isHeader = true)
        TableCell("RPE", weight = 1f, isHeader = true)
        TableCell("Notes", weight = 2f, isHeader = true)

    }
}

@Composable
fun currentBlock(
    blockName: String,
    lengthWeeks: String,
    StartDate: String
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.08f)))
    {
        Column(
            modifier = Modifier.padding(20.dp))
        {
            Text(
                text = blockName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = lengthWeeks,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f)
            )

            Text(
                text = "startDate",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

        }
    }
}
@Composable
fun BlockCard(
    blockName: String,
    lengthWeeks: String,
    startDate: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .height(96.dp)
                .background(PrimaryRed)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E).copy(alpha = 0.92f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    text = blockName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = lengthWeeks,
                    color = Color.White.copy(alpha = 0.78f)
                )

                Text(
                    text = startDate,
                    color = Color.White.copy(alpha = 0.62f)
                )
            }
        }
    }
}