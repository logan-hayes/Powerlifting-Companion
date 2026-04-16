package com.example.powerlifter_companion.viewmodel

import androidx.lifecycle.ViewModel
import com.example.powerlifter_companion.data.ExerciseRepository


class ExerciseViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

    val exerciseDefinitions = repository.getAllExerciseDefinitions()
}
