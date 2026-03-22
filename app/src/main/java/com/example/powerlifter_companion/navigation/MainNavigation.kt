
package com.example.powerlifter_companion.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import com.example.powerlifter_companion.ui.HomeScreen
import com.example.powerlifter_companion.ui.performanceUi
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
import androidx.compose.ui.Modifier

@Composable
fun BottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) ->Unit
    ){
    NavigationBar{

        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.Home, "")
        },
            label = {Text(text = "Home")},
            selected = (selectedIndex == 0),
            onClick = {onItemSelected(0)}
        )

        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.Star, "")
        },
            label = {Text(text = "Performance")},
            selected = (selectedIndex == 1),
            onClick = {onItemSelected(1) }
        )

        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.List, "")
        },
            label = {Text(text = "Blocks")},
            selected = (selectedIndex == 2),
            onClick = {onItemSelected(2)
            })

        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.Person, "")
        },
            label = {Text(text = "Profile")},
            selected = (selectedIndex == 3),
            onClick = {onItemSelected(3)
            })
    }
}

@Composable
fun Scaffolding() {
    val selectedIndex = remember { mutableStateOf(0) }
    Scaffold(
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
                1 -> performanceUi()
                2 -> blockUi()
                3 -> profileUi()
            }
        }
    }
}