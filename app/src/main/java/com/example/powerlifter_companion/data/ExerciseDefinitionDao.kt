package com.example.powerlifter_companion.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.MuscleGroup
import kotlinx.coroutines.flow.Flow

/**
 * @author Logan Hayes
 *
 *Class for holding database operations for ExerciseDefinition objects.
 * */

@Dao
interface ExerciseDefinitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseDefinition(exerciseDefinition: ExerciseDefinition)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseDefinitionList(exerciseDefinitionList: List<ExerciseDefinition>)

    @Delete
    suspend fun DeleteExerciseDefinition(exerciseDefinition: ExerciseDefinition)

    @Query("SELECT * FROM exercise_definitions WHERE category = :category ORDER BY name ASC")
    fun getExerciseDefinitionsByCategory(category: ExerciseCategory): Flow<List<ExerciseDefinition>>

    @Query("SELECT * FROM exercise_definitions WHERE muscleGroup = :muscleGroup ORDER BY name ASC")
    fun getExerciseDefinitionsByMuscleGroup(muscleGroup: MuscleGroup): Flow<List<ExerciseDefinition>>

    @Query("SELECT * FROM exercise_definitions ORDER BY name ASC")
    fun getAllExerciseDefinitions(): Flow<List<ExerciseDefinition>>

    @Query("SELECT COUNT(*) FROM exercise_definitions")
    suspend fun getExerciseDefinitionCount(): Int
}