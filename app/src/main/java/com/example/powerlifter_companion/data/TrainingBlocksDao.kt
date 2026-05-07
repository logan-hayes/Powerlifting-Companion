package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.powerlifter_companion.entities.TrainingBlocks
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingBlocksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingBlock(trainingBlock: TrainingBlocks): Long

    @Update
    suspend fun updateTrainingBlock(trainingBlock: TrainingBlocks)

    @Delete
    suspend fun deleteTrainingBlock(trainingBlock: TrainingBlocks)

    @Query("SELECT * FROM training_block WHERE block_id = :trainingBlockId")
    fun getTrainingBlock(trainingBlockId: Long): Flow<TrainingBlocks>

    @Query("SELECT * FROM training_block")
    fun getAllTrainingBlocks(): Flow<List<TrainingBlocks>>
}