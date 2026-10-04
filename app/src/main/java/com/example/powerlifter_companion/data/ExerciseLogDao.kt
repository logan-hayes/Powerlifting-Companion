package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.powerlifter_companion.entities.ExerciseLog
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseLogDao {

    @Insert
    suspend fun insertExerciseLog(exerciseLog: ExerciseLog)

    @Insert
    suspend fun insertExerciseLogs(exerciseLogs: List<ExerciseLog>)

    @Query("SELECT * FROM exercise_log WHERE post_workout_id = :postWorkoutId")
    fun getExerciseLogsForPostWorkout(postWorkoutId: Int): Flow<List<ExerciseLog>>
}
