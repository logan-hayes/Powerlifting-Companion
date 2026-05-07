
package com.example.powerlifter_companion.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "post_workout",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["workout_id"],
            childColumns = ["workout_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Users::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["workout_id"]),
        Index(value = ["user_id"])
    ]
)
data class PostWorkout(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_workout_id")
    val postWorkoutId: Int = 0,
    @ColumnInfo(name = "workout_id")
    val workoutId: Long,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    val completedTimeStamp: Long,
    val completedAsPlanned: Boolean,
    val notes: String? = null
)
