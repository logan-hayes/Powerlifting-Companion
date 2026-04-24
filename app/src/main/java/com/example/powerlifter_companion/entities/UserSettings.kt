package com.example.powerlifter_companion.entities

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index


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
    @ColumnInfo(name = "language")
    val language: String = "en-US",
    val darkTheme: Boolean = false,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    //later change from boolean to enum
    @ColumnInfo(name = "notification_preferences")
    val notificationPreferences: Boolean,
    @ColumnInfo(name = "lbs_view")
    val lbsView: Boolean = true
)
