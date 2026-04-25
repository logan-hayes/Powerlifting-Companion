package com.example.powerlifter_companion.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import com.example.powerlifter_companion.entities.Users
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

    @Query("SELECT * FROM training_blocks WHERE training_block_id = :trainingBlockId")
        fun getTrainingBlock(trainingBlockId: Long): Flow<TrainingBlocks>

    @Query("SELECT * FROM training_blocks")
    fun getAllTrainingBlocks(): Flow<List<TrainingBlocks>>
}