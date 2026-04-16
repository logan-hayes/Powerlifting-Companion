package com.example.powerlifter_companion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed


@Composable
fun BlockDetailUI(){
    val blockGradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(blockGradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "Block Detail Test",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        BlockTableHeader()

        Spacer(modifier = Modifier.height(10.dp))

        BlockTableRow("Squat", "3", "7", "405", "8", "")
        BlockTableRow("Bench", "5", "5", "285", "", "Slow eccentric")
        BlockTableRow("Tricep extentions", "3", "15", "", "8", "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockDetailHeader(){
    TopAppBar(
        title = {
            Text(
                text = "Blocks",
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