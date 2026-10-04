package com.example.powerlifter_companion.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * The structured exercise-level deviation record the earlier data-model
 * review flagged as missing: one row per exercise per completed workout,
 * capturing what actually happened versus what was planned (Exercise).
 */
@Entity(
    tableName = "exercise_log",
    foreignKeys = [
        ForeignKey(
            entity = PostWorkout::class,
            parentColumns = ["post_workout_id"],
            childColumns = ["post_workout_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["exerciseID"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["post_workout_id"]),
        Index(value = ["exercise_id"])
    ]
)
data class ExerciseLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exercise_log_id")
    val exerciseLogId: Int = 0,
    @ColumnInfo(name = "post_workout_id")
    val postWorkoutId: Int,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Int,
    val status: ExerciseLogStatus,
    val actualSets: Int? = null,
    val actualReps: Int? = null,
    val actualWeight: Int? = null,
    val actualRpe: Float? = null
)
