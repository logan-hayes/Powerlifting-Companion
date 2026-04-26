package com.example.powerlifter_companion.entities

/**
 * @author Logan Hayes
 *
 *
 *
 * */

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "exercise_definition")
data class ExerciseDefinition(

    @PrimaryKey(autoGenerate = true)
    val exerciseDefinitionID: Int = 0,

    val name: String,
    val category: ExerciseCategory? = null,
    val muscleGroup: MuscleGroup? = null,
    val isCustom: Boolean = false)
    {
}

