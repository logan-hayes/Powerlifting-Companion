package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.powerlifter_companion.entities.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Users): Long

    @Update
    suspend fun updateUser(users:Users)

    @Delete
    suspend fun deleteUsers(user: Users)

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserById(userId: Long): Users?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<Users>>

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getCurrentUser(): Users?
}