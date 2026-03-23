package com.example.powerlifter_companion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed

@Composable
fun PerformanceUi(){
    val gradientBlack = Brush.verticalGradient(
        colors = listOf(
            BackgroundGray,
            PrimaryRed
        )
    )
    Column(
        modifier = Modifier.fillMaxSize()
        .background(gradientBlack),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
     Text("Performance UI")
    }
}