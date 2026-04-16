package com.example.powerlifter_companion.entities

/**
 * @author Logan Hayes
 *
 *
 *
 * */


import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(

    @PrimaryKey(autoGenerate = true)
    var exerciseID: Int = 0,

    val exerciseDefinition: Int,

    val sets: Int,
    val reps: Int,
    val weight: Int?,
    val rpe: Float?,
    val notes: String?,
    val targetWeight: Int?,
    val maxPercentage: Float?
)


