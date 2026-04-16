package com.example.powerlifter_companion.data

import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.MuscleGroup

/**
 * @author Logan Hayes
 *
 *
 *
 * */

class ExerciseRepository(
    private val exerciseDefinitionDao: ExerciseDefinitionDao
){
    fun getAllExerciseDefinitions() =
        exerciseDefinitionDao.getAllExerciseDefinitions()

    fun getByCategory(category: ExerciseCategory) =
        exerciseDefinitionDao.getExerciseDefinitionsByCategory(category)

    fun getByMuscleGroup(muscleGroup: MuscleGroup) =
        exerciseDefinitionDao.getExerciseDefinitionsByMuscleGroup(muscleGroup)
}

