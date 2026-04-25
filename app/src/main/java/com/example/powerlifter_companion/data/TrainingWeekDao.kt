package com.example.powerlifter_companion.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingWeekDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingWeek(trainingWeek: TrainingWeek)

    @Update
    suspend fun updateTrainingWeek(trainingBlock:TrainingWeek)

    @Delete
    suspend fun deleteTrainingWeek(trainingWeek: TrainingWeek)

    @Query("SELECT * FROM training_week WHERE training_week_id = :trainingWeekId")
    fun  getTrainingWeek(trainingWeekId: Long): TrainingWeek?

    @Query("SELECT * FROM training_week WHERE block_id = :trainingBlockId")
    fun getAllTrainingWeeksInBlock(trainingBlockId: Long): Flow<List<TrainingWeek>>

    @Query("DELETE FROM training_week WHERE block_id = :trainingBlockId")
    fun deleteWeeksForBlock(trainingBlockId: Long): Flow<TrainingBlocks>
}