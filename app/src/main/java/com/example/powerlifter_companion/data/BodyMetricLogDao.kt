package com.example.powerlifter_companion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.powerlifter_companion.entities.BodyMetricLog
import com.example.powerlifter_companion.entities.BodyMetricType
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyMetricLogDao {

    @Insert
    suspend fun insertBodyMetricLog(log: BodyMetricLog): Long

    @Delete
    suspend fun deleteBodyMetricLog(log: BodyMetricLog)

    // Bodyweight history for a user (exerciseDefinitionId is always null here).
    @Query(
        """
        SELECT * FROM body_metric_log
        WHERE user_id = :userId AND metric_type = :metricType
        ORDER BY recorded_at ASC
        """
    )
    fun getMetricHistory(userId: Long, metricType: BodyMetricType): Flow<List<BodyMetricLog>>

    // Estimated-1RM history for one specific lift.
    @Query(
        """
        SELECT * FROM body_metric_log
        WHERE user_id = :userId
            AND metric_type = 'ESTIMATED_ONE_REP_MAX'
            AND exercise_definition_id = :exerciseDefinitionId
        ORDER BY recorded_at ASC
        """
    )
    fun getOneRepMaxHistory(userId: Long, exerciseDefinitionId: Int): Flow<List<BodyMetricLog>>

    @Query(
        """
        SELECT * FROM body_metric_log
        WHERE user_id = :userId AND metric_type = :metricType
        ORDER BY recorded_at DESC
        LIMIT 1
        """
    )
    suspend fun getLatestMetric(userId: Long, metricType: BodyMetricType): BodyMetricLog?

    @Query(
        """
        SELECT * FROM body_metric_log
        WHERE user_id = :userId
            AND metric_type = 'ESTIMATED_ONE_REP_MAX'
            AND exercise_definition_id = :exerciseDefinitionId
        ORDER BY recorded_at DESC
        LIMIT 1
        """
    )
    suspend fun getLatestOneRepMax(userId: Long, exerciseDefinitionId: Int): BodyMetricLog?
}
