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

    @Query("SELECT * FROM workout WHERE training_week_id = :trainingWeekId ORDER BY workout_order ASC")
    fun getAllWorkoutsInTrainingWeek(trainingWeekId: Long): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE workout_id = :workoutId")
    suspend fun getWorkoutById(workoutId: Long): Workout?
}