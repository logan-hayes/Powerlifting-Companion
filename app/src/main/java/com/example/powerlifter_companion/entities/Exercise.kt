package com.example.powerlifter_companion.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["workout_id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["workoutId"])
    ]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    var exerciseID: Int = 0,

    val workoutId: Long,
    val exerciseDefinition: Int,

    val sets: Int,
    val reps: Int,
    val weight: Int?,
    val rpe: Float?,
    val notes: String?,
    val targetWeight: Int?,
    val maxPercentage: Float?
)


