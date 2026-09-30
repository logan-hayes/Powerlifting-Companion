package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.powerlifter_companion.entities.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workout WHERE training_week_id = :trainingWeekId ORDER BY day_number ASC")
    fun getAllWorkoutsInTrainingWeek(trainingWeekId: Long): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE workout_id = :workoutId")
    fun getWorkoutById(workoutId: Long): Flow<Workout?>

    // Added for the block overview redesign: every workout in a block, joined
    // through training_week since Workout only stores training_week_id.
    @Query(
        """
        SELECT workout.* FROM workout
        INNER JOIN training_week ON workout.training_week_id = training_week.training_week_id
        WHERE training_week.block_id = :blockId
        ORDER BY training_week.week_number ASC, workout.day_number ASC
        """
    )
    fun getAllWorkoutsInBlock(blockId: Long): Flow<List<Workout>>
}
