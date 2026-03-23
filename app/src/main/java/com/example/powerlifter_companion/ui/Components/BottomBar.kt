package com.example.powerlifter_companion.ui.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) ->Unit
){
    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedIconColor = Color.White.copy(alpha = 0.6f),
        unselectedTextColor = Color.White.copy(alpha = 0.6f)
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary)
    {
        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.Home, "")
        },
            label = {Text(text = "Home")},
            selected = (selectedIndex == 0),
            onClick = {onItemSelected(0)},
            colors = navItemColors
        )

        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.Star, "")
        },
            label = {Text(text = "Performance")},
            selected = (selectedIndex == 1),
            onClick = {onItemSelected(1) },
            colors = navItemColors
        )

        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.List, "")
        },
            label = {Text(text = "Blocks")},
            selected = (selectedIndex == 2),
            onClick = {onItemSelected(2)},
            colors = navItemColors
        )

        NavigationBarItem(icon = {
            Icon(imageVector = Icons.Default.Person, "")

        },
            label = {Text(text = "Profile")},
            selected = (selectedIndex == 3),
            onClick = {onItemSelected(3)},
            colors = navItemColors
        )
    }
}