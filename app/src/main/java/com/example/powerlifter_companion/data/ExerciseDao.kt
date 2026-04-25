package com.example.powerlifter_companion.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.MuscleGroup
import kotlinx.coroutines.flow.Flow

/**
 * @author Logan Hayes
 *
 *Class for holding database operations for Exercise objects.
 * */

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

    @Query("SELECT * FROM exercise_definitions WHERE exerciseDefinitionId = :definitionId")
        fun getExerciseByDefinitionId(definitionId: Int): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE workoutId = :WorkoutId")
        fun getAllExercisesInWorkout(workoutId: Long): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE exerciseId = :exerciseId")
        fun getExerciseById(ExerciseId: Long)
}

