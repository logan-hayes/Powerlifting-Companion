package com.example.powerlifter_companion.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
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

    @Query("SELECT * FROM workout " +
            "WHERE training_week_id = :trainingWeekId" +
            "Order By workout_order ASC")
    fun getAllWorkoutsInTrainingWeek(trainingWeekId: Long): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE workout_id = :workoutId")
    fun getWorkoutById(workoutId: Long): Flow<Workout>
}