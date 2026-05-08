package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.powerlifter_companion.entities.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSettings(userSettings: UserSettings)

    @Update
    suspend fun updateUserSettings(userSettings: UserSettings)

    @Query("SELECT * FROM user_settings WHERE user_id = :userId")
    fun getUserSettings(userId: Long): Flow<UserSettings?>

    @Query("SELECT * FROM user_settings LIMIT 1")
    suspend fun getCurrentUserSettings(): UserSettings?
}
