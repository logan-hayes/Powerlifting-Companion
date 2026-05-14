
package com.example.powerlifter_companion.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.powerlifter_companion.ui.HomeScreen
import com.example.powerlifter_companion.ui.PerformanceUi
import com.example.powerlifter_companion.ui.blockUi
import com.example.powerlifter_companion.ui.profileUi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.powerlifter_companion.data.AppDatabase
import com.example.powerlifter_companion.data.TrainingRepository
import com.example.powerlifter_companion.ui.Components.BottomBar
import com.example.powerlifter_companion.ui.Components.topBarHome
import com.example.powerlifter_companion.ui.PerformanceHeader
import com.example.powerlifter_companion.ui.blockHeader
import com.example.powerlifter_companion.ui.profileHeader
import com.example.powerlifter_companion.viewmodel.TrainingViewModel

@Composable
fun Scaffolding() {
    val context = LocalContext.current

    val trainingViewModel = remember {
        val db = AppDatabase.getDatabase(context)

        TrainingViewModel(
            TrainingRepository(
                trainingBlocksDao = db.trainingBlocksDao(),
                trainingWeekDao = db.trainingWeekDao(),
                workoutDao = db.workoutDao(),
                exerciseDao = db.exerciseDao(),
                exerciseDefinitionDao = db.exerciseDefinitionDao()
            )
        )
    }
    LaunchedEffect(Unit) {
        trainingViewModel.seedExercises()
    }

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
                0 -> HomeScreen(
                    onGoToCurrentWorkout = {
                        selectedIndex.value = 2
                    }
                )
                1 -> PerformanceUi()
                2 -> blockUi(trainingViewModel)
                3 -> profileUi()
            }
        }
    }
}