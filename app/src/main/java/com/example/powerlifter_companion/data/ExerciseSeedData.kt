package com.example.powerlifter_companion.data

import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.MuscleGroup

/**
 * @author Logan Hayes
 *
 *
 * */

object ExerciseSeedData {

    val defaultExercises = listOf(
        ExerciseDefinition(
            name = "Squat",
            category = ExerciseCategory.COMPETITION,
            muscleGroup = MuscleGroup.QUADS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Bench Press",
            category = ExerciseCategory.COMPETITION,
            muscleGroup = MuscleGroup.CHEST,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Deadlift",
            category = ExerciseCategory.COMPETITION,
            muscleGroup = MuscleGroup.BACK,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Romanian Deadlift",
            category = ExerciseCategory.COMPETITION_VARIATION,
            muscleGroup = MuscleGroup.HAMSTRINGS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Romanian Deadlift",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.HAMSTRINGS,
            isCustom = false
        )
    )
}