package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.powerlifter_companion.entities.PostWorkout

@Dao
interface PostWorkoutDao {

    // Returns the generated post_workout_id so the caller can link
    // ExerciseLog rows to this PostWorkout.
    @Insert
    suspend fun insertPostWorkout(postWorkout: PostWorkout): Long

    @Delete
    suspend fun deletePostWorkout(postWorkout: PostWorkout)

    @Query("DELETE FROM post_workout WHERE post_workout_id = :postWorkoutId")
    suspend fun deletePostWorkoutById(postWorkoutId: Int)

    @Query("SELECT * FROM post_workout WHERE workout_id = :workoutId")
    fun getPostWorkoutByWorkoutId(workoutId: Long): Flow<PostWorkout?>

    // Lets a re-completion overwrite the previous log instead of stacking a
    // duplicate row (ExerciseLog rows cascade-delete with it).
    @Query("DELETE FROM post_workout WHERE workout_id = :workoutId")
    suspend fun deletePostWorkoutForWorkout(workoutId: Long)

    // Block-scoped read so the overview can show a completed badge per workout.
    @Query(
        """
        SELECT post_workout.* FROM post_workout
        INNER JOIN workout ON post_workout.workout_id = workout.workout_id
        INNER JOIN training_week ON workout.training_week_id = training_week.training_week_id
        WHERE training_week.block_id = :blockId
        """
    )
    fun getPostWorkoutsInBlock(blockId: Long): Flow<List<PostWorkout>>
}
