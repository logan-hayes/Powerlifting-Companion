package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.powerlifter_companion.entities.TrainingWeek
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingWeekDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingWeek(trainingWeek: TrainingWeek)

    @Update
    suspend fun updateTrainingWeek(trainingWeek: TrainingWeek)

    @Delete
    suspend fun deleteTrainingWeek(trainingWeek: TrainingWeek)

    @Query("SELECT * FROM training_week WHERE training_week_id = :trainingWeekId")
    suspend fun getTrainingWeek(trainingWeekId: Long): TrainingWeek?

    @Query("SELECT * FROM training_week WHERE block_id = :trainingBlockId")
    fun getAllTrainingWeeksInBlock(trainingBlockId: Long): Flow<List<TrainingWeek>>

    @Query("DELETE FROM training_week WHERE block_id = :trainingBlockId")
    suspend fun deleteWeeksForBlock(trainingBlockId: Long): Int
}
