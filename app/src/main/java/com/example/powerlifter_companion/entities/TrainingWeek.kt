package com.example.powerlifter_companion.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "training_week",
    foreignKeys = [
        ForeignKey(
            entity = TrainingBlocks::class,
            parentColumns = ["block_id"],
            childColumns = ["block_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["block_id"])
    ]
)
data class TrainingWeek(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_week_id")
    val trainingWeekId: Long = 0,
    @ColumnInfo(name = "block_id")
    val blockId: Long,
    @ColumnInfo(name = "week_number")
    val weekNumber: Int
)
