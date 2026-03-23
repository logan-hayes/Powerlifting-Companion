package com.example.powerlifter_companion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Brush
import com.example.powerlifter_companion.ui.theme.BackgroundGray

import androidx.compose.ui.graphics.Color
import com.example.powerlifter_companion.ui.theme.PrimaryRed


@Composable
fun HomeScreen(){
    val gradientBlack = Brush.verticalGradient(
        colors = listOf(
            BackgroundGray,
            PrimaryRed
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBlack)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ){
        Text("Home screen UI test")

    }
}