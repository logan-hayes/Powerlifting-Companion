package com.example.powerlifter_companion.entities

/**
 * @author Logan Hayes
 *
 *
 *
 * */


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey



@Entity(tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["workout_id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["workout_id"])
    ]
)data class Exercise(

    @PrimaryKey(autoGenerate = true)
    var exerciseID: Int = 0,
    @ColumnInfo(name = "workout_id")
    val workoutId: Long,
    @ColumnInfo(name = "exercise_definition")
    val exerciseDefinition: Int,
    val sets: Int?,
    val reps: Int,
    val weight: Int?,
    val rpe: Float?,
    val notes: String?,
    val targetWeight: Int?,
    val maxPercentage: Float?
)


