package com.example.powerlifter_companion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.powerlifter_companion.data.ExerciseRepository
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.MuscleGroup
import com.example.powerlifter_companion.entities.ExerciseCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    val exerciseDefinitions = exerciseRepository.getAllExerciseDefinitions()


    private val _exerciseName = MutableStateFlow("")
    val exerciseName: StateFlow<String> = _exerciseName

    private val _exerciseCategory = MutableStateFlow<ExerciseCategory?>(null)
    val exerciseCategory: StateFlow<ExerciseCategory?> = _exerciseCategory

    private val _exerciseMuscleGroup = MutableStateFlow<MuscleGroup?>(null)
    val exerciseMuscleGroup: StateFlow<MuscleGroup?> = _exerciseMuscleGroup

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun updateExerciseName(name: String) {
        _exerciseName.value = name
    }

    fun updateExerciseCategory(exerciseCategory: ExerciseCategory?) {
        _exerciseCategory.value = exerciseCategory
    }

    fun updateMuscleGroup(muscleGroup: MuscleGroup?) {
        _exerciseMuscleGroup.value = muscleGroup
    }

    fun addExercise(){
        val name = _exerciseName.value.trim()
        val category = _exerciseCategory.value
        val muscleGroup = _exerciseMuscleGroup.value

        if (name.isBlank()) {
            _errorMessage.value = "exercise cannot be left blank"
            return
        }

        viewModelScope.launch{
            exerciseRepository.addExerciseDefinition(
                ExerciseDefinition(
                    name = name,
                    category = category,
                    muscleGroup = muscleGroup,
                    isCustom = true
                )
            )
            //repository call
            _exerciseName.value = ""
            _exerciseCategory.value = null
            _exerciseMuscleGroup.value = null
            _errorMessage.value = null
        }
    }

    fun deleteExercise(exercise: ExerciseDefinition){
        if(!exercise.isCustom) {
            _errorMessage.value = "Default exercises cannot be deleted"
            return
        }

        viewModelScope.launch {
            exerciseRepository.deleteExerciseDefinition(exercise)
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
