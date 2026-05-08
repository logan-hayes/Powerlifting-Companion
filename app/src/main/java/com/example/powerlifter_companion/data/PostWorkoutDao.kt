package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.powerlifter_companion.entities.PostWorkout

@Dao
interface PostWorkoutDao {

    @Insert
    suspend fun insertPostWorkout(postWorkout: PostWorkout)

    @Delete
    suspend fun deletePostWorkout(postWorkout: PostWorkout)

    @Query("DELETE FROM post_workout WHERE post_workout_id = :postWorkoutId")
    suspend fun deletePostWorkoutById(postWorkoutId: Int)

    @Query("SELECT * FROM post_workout WHERE workout_id = :workoutId")
    fun getPostWorkoutByWorkoutId(workoutId: Long): Flow<PostWorkout?>
}
