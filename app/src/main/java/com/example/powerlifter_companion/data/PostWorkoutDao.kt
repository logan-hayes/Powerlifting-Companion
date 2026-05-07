package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.powerlifter_companion.entities.PostWorkout
import kotlinx.coroutines.flow.Flow

@Dao
interface PostWorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostWorkout(postWorkout: PostWorkout)

    @Delete
    suspend fun deletePostWorkout(postWorkout: PostWorkout)

    @Query("SELECT * FROM post_workout WHERE workout_id = :workoutId")
    fun getPostWorkoutByWorkoutId(workoutId: Long): Flow<PostWorkout?>
}