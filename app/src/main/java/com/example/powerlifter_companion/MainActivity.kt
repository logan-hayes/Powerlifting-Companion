package com.example.powerlifter_companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.powerlifter_companion.navigation.Scaffolding
import com.example.powerlifter_companion.ui.HomeScreen
import com.example.powerlifter_companion.ui.theme.PowerlifterCompanionTheme
import com.example.powerlifter_companion.viewmodel.ExerciseViewModel
import com.example.powerlifter_companion.viewmodel.TrainingViewModel
import com.example.powerlifter_companion.viewmodel.ViewModelFactory

import android.util.Log

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate started")

        try {
            val app = application as PowerlifterApplication
            Log.d(TAG, "Casting to PowerlifterApplication successful")

            val factory = ViewModelFactory(app.exerciseRepository, app.trainingRepository)
            Log.d(TAG, "ViewModelFactory created")

            enableEdgeToEdge()
            setContent {
                Log.d(TAG, "setContent started")
                val trainingViewModel: TrainingViewModel = viewModel(factory = factory)
                val exerciseViewModel: ExerciseViewModel = viewModel(factory = factory)

                PowerlifterCompanionTheme {
                    Scaffolding(trainingViewModel, exerciseViewModel)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Critical error during MainActivity initialization", e)
            throw e
        }
    }
}