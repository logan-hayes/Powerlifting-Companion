package com.example.powerlifter_companion.viewmodel

import androidx.lifecycle.ViewModel
import com.example.powerlifter_companion.data.ExerciseRepository
import com.example.powerlifter_companion.data.TrainingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest

class TrainingViewModel(
    private val trainingRepository: TrainingRepository
) : ViewModel() {

    private val _selectedBlockId = MutableStateFlow<Long?>(null)
    val selectedBlockId: StateFlow<Long?> = _selectedBlockId

    private val _selectedTrainingWeekId = MutableStateFlow<Long?>(null)
    val selectedTrainingWeekId: StateFlow<Long?> = _selectedTrainingWeekId

    private val _selectedExerciseId = MutableStateFlow<Long?>(null)
    val selectedExerciseId: StateFlow<Long?> = _selectedExerciseId

    private val _selectedWorkoutId = MutableStateFlow<Long?>(null)
    val selectedWorkoutId: StateFlow<Long?> = _selectedWorkoutId

    val exercisesInWorkout =
        _selectedWorkoutId
            .filterNotNull()
            .flatMapLatest { workoutId ->
                trainingRepository.getExercisesInWorkout(workoutId)
            }
    val workoutsInTrainingWeek =
        _selectedTrainingWeekId
            .filterNotNull()
            .flatMapLatest { trainingWeekId ->
                trainingRepository.getWorkoutsInWeek(trainingWeekId)
            }
    val trainingWeeksInBlock =
        _selectedBlockId
            .filterNotNull()
            .flatMapLatest { workoutId ->
                trainingRepository.getWeeksInBlock(workoutId)
            }
    //Block Specific//

    fun selectTrainingBlock(blockId: Long) {
        _selectedBlockId.value = blockId
    }

    //Week Specific//

    fun selectTrainingWeek(trainingWeekId: Long) {
        _selectedTrainingWeekId.value = trainingWeekId
    }

    //Workout Specific//
    fun selectWorkout(workoutId: Long) {
        _selectedWorkoutId.value = workoutId
    }

    //Exercise Specific//



}
