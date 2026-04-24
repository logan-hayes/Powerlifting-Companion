package com.example.powerlifter_companion.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query

import kotlinx.coroutines.flow.Flow
import com.example.powerlifter_companion.entities.PostWorkout

@Dao
interface PostWorkoutDao {

    @Insert
    suspend fun insertPostWorkout(postWorkout: PostWorkout)

    @Delete
    suspend fun deletePostWorkout(postWorkoutId: Long)

    @Query("SELECT * FROM post_workout WHERE workout_id = :workoutId")
    fun getPostWorkoutByWorkoutId(workoutId: Long): Flow<PostWorkout>
}