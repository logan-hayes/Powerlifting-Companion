
package com.example.powerlifter_companion.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import com.example.powerlifter_companion.ui.HomeScreen
import com.example.powerlifter_companion.ui.PerformanceUi
import com.example.powerlifter_companion.ui.blockUi
import com.example.powerlifter_companion.ui.profileUi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.powerlifter_companion.ui.Components.BottomBar
import com.example.powerlifter_companion.ui.Components.topBarHome

@Composable
fun Scaffolding() {
    val selectedIndex = remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            topBarHome()
        },

        bottomBar = {
            BottomBar(
                selectedIndex.value,
                onItemSelected = { selectedIndex.value = it }
            )
        }
    ) { innerpadding ->

        Box(modifier = Modifier.padding(innerpadding)) {
            when (selectedIndex.value) {
                0 -> HomeScreen()
                1 -> PerformanceUi()
                2 -> blockUi()
                3 -> profileUi()
            }
        }
    }
}