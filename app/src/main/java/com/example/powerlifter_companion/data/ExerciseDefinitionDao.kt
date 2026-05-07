package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.MuscleGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDefinitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseDefinition(exerciseDefinition: ExerciseDefinition)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseDefinitionList(exerciseDefinitionList: List<ExerciseDefinition>)

    @Delete
    suspend fun deleteExerciseDefinition(exerciseDefinition: ExerciseDefinition)

    @Query("SELECT * FROM exercise_definition WHERE category = :category ORDER BY name ASC")
    fun getExerciseDefinitionsByCategory(category: ExerciseCategory): Flow<List<ExerciseDefinition>>

    @Query("SELECT * FROM exercise_definition WHERE muscleGroup = :muscleGroup ORDER BY name ASC")
    fun getExerciseDefinitionsByMuscleGroup(muscleGroup: MuscleGroup): Flow<List<ExerciseDefinition>>

    @Query("SELECT * FROM exercise_definition ORDER BY name ASC")
    fun getAllExerciseDefinitions(): Flow<List<ExerciseDefinition>>

    @Query("SELECT COUNT(*) FROM exercise_definition")
    suspend fun getExerciseDefinitionCount(): Int
}