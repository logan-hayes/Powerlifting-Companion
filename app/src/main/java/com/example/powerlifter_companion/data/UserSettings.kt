package com.example.powerlifter_companion.data

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSettings {

    @Insert
    suspend fun insert(user: Users)

    @Update
    suspend fun updateUserSettings(user: Users)

    @Query("SELECT * FROM user_settings WHERE user_settings = :userSettings")
    fun getUserSettings(userId: Long): UserSettings?
}