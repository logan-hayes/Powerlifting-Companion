
package com.example.powerlifter_companion.entities

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(tableName = "post_workout",
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
        Index(value = ["workout_id"])
    ]
)

data class PostWorkout(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_workout_id")
    val postWorkoutId: Int,
    @ColumnInfo(name = "block_id")
    val blockId: Long = 0,
    val completedTimeStamp: Long
)
