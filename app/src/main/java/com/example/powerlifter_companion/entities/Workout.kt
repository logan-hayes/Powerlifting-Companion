package com.example.powerlifter_companion.entities

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(tableName = "workout",
    foreignKeys = [
        ForeignKey(
            entity = TrainingWeek::class,
            parentColumns = ["training_week_id"],
            childColumns = ["training_week_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["training_week_id"])
    ]
)
data class Workout(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_id")
    val workoutId: Long,
    val notes: Long,
    @ColumnInfo(name = "day_number")
    val dayNumber: Int
)
