package com.example.powerlifter_companion.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "user_settings",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"])
    ]
)
data class UserSettings(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name = "language")
    val language: String = "en-US",
    val darkTheme: Boolean = false,
    //later change from boolean to enum
    @ColumnInfo(name = "notification_preferences")
    val notificationPreferences: Boolean,
    @ColumnInfo(name = "lbs_view")
    val lbsView: Boolean = true
)
