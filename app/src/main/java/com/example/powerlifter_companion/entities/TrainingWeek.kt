package com.example.powerlifter_companion.entities

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "training_week",
    foreignKeys = [
        ForeignKey(
            entity = TrainingBlocks::class,
            parentColumns = ["blockId"],
            childColumns = ["blockId"],
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