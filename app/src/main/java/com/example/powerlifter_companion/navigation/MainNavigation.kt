
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
import androidx.compose.ui.Modifier
import com.example.powerlifter_companion.ui.Components.BottomBar
import com.example.powerlifter_companion.ui.Components.topBarHome
import com.example.powerlifter_companion.ui.PerformanceHeader
import com.example.powerlifter_companion.ui.blockHeader
import com.example.powerlifter_companion.ui.profileHeader

@Composable
fun Scaffolding() {
    val selectedIndex = remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            when (selectedIndex.value){
                0 -> topBarHome()
                1 -> PerformanceHeader()
                2 -> blockHeader()
                3 -> profileHeader()

            }
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