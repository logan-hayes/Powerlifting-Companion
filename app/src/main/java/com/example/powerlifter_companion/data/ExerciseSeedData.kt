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
            name = "Dumbbell Curl",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.BICEPS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Preacher Curl",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.BICEPS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Incline Dumbbell Curl",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.BICEPS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Arnold Press",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.SHOULDERS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Dumbell Rear Delt Raise",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.SHOULDERS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Dumbell Side Raise",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.SHOULDERS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Weighted Lunge",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.QUADS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Belt Squat",
            category = ExerciseCategory.COMPETITION_VARIATION,
            muscleGroup = MuscleGroup.POSTERIOR_CHAIN,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "REVERSE HYPER",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.POSTERIOR_CHAIN,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Farmer's Carry",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.GRIP_FOREARMS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Leg Raises",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.QUADS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Floor press",
            category = ExerciseCategory.COMPETITION_VARIATION,
            muscleGroup = MuscleGroup.CHEST,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Tricep Extensions",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.TRICEPS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "shoulder Dumbbell press",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.SHOULDERS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Hamstring curl",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.HAMSTRINGS,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Weighted Calf Raises",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.CALVES,
            isCustom = false
        ),

        ExerciseDefinition(
            name = "Leg Raises",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.QUADS,
            isCustom = false
        ),



        ExerciseDefinition(
            name = "Bent over Barbell Rows",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.BACK,
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
            name = "Chest Supported Row",
            category = ExerciseCategory.ACCESSORY,
            muscleGroup = MuscleGroup.BACK,
            isCustom = false
        ),
    )
}