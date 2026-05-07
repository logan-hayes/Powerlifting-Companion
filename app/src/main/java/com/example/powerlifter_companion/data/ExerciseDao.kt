package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.powerlifter_companion.entities.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise ORDER BY exerciseID ASC")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE exerciseDefinition = :definitionId")
    fun getExerciseByDefinitionId(definitionId: Int): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE workoutId = :workoutId")
    fun getAllExercisesInWorkout(workoutId: Long): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE exerciseId = :exerciseId")
    suspend fun getExerciseById(exerciseId: Long): Exercise?
}
