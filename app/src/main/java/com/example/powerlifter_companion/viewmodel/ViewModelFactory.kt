package com.example.powerlifter_companion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.powerlifter_companion.data.ExerciseRepository
import com.example.powerlifter_companion.data.TrainingRepository

class ViewModelFactory(
    private val exerciseRepository: ExerciseRepository,
    private val trainingRepository: TrainingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ExerciseViewModel::class.java) -> {
                ExerciseViewModel(exerciseRepository) as T
            }
            modelClass.isAssignableFrom(TrainingViewModel::class.java) -> {
                TrainingViewModel(trainingRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
