package com.example.powerlifter_companion.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout",
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
    val workoutId: Long = 0,
    
    @ColumnInfo(name = "training_week_id")
    val trainingWeekId: Long,
    
    @ColumnInfo(name = "workout_order")
    val workoutOrder: Int,
    
    val notes: String?,
    
    @ColumnInfo(name = "day_number")
    val dayNumber: Int
)
