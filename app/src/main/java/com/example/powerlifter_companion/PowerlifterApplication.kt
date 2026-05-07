package com.example.powerlifter_companion

import android.app.Application
import android.util.Log
import com.example.powerlifter_companion.data.AppDatabase
import com.example.powerlifter_companion.data.ExerciseRepository
import com.example.powerlifter_companion.data.TrainingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PowerlifterApplication : Application() {
    private val TAG = "PowerlifterApp"

    // No need for a global scope if only seeding on create, but good for other background tasks
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application onCreate started")
    }

    val database: AppDatabase by lazy {
        Log.d(TAG, "Initializing Database")
        AppDatabase.getInstance(this, applicationScope)
    }

    val exerciseRepository: ExerciseRepository by lazy {
        Log.d(TAG, "Initializing ExerciseRepository")
        ExerciseRepository(database.exerciseDefinitionDao())
    }

    val trainingRepository: TrainingRepository by lazy {
        Log.d(TAG, "Initializing TrainingRepository")
        TrainingRepository(
            database.trainingBlocksDao(),
            database.trainingWeekDao(),
            database.workoutDao(),
            database.exerciseDao()
        )
    }
}
