package com.example.powerlifter_companion.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun blockUi(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        currentBlock(
            blockName = "TestBlock",
            lengthWeeks = "7 days",
            StartDate = "4/1/26")
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
            color = if (isHeader)
                MaterialTheme.colorScheme.onBackground
            else
                MaterialTheme.colorScheme.onSurface,
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

