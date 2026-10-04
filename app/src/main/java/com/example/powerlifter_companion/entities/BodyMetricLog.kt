package com.example.powerlifter_companion.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Time-series history for bodyweight and estimated 1RM. Both were previously
 * single-value fields with no persistence at all (bodyweight had no field
 * anywhere; 1RM was transient ViewModel state, never saved) — every update
 * would have overwritten the last one, making trend charts and DOTS/Wilks
 * history impossible. One table with a metricType discriminator instead of
 * two separate tables, since both are "a number over time for this user"
 * and will likely share one chart/history screen.
 *
 * exerciseDefinitionId is null for BODYWEIGHT rows and required for
 * ESTIMATED_ONE_REP_MAX rows (which lift — squat/bench/deadlift/etc). A
 * null foreign key value is not enforced by SQLite, so this is safe to leave
 * nullable rather than splitting into two tables.
 */
@Entity(
    tableName = "body_metric_log",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseDefinition::class,
            parentColumns = ["exerciseDefinitionID"],
            childColumns = ["exercise_definition_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["exercise_definition_id"])
    ]
)
data class BodyMetricLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "body_metric_log_id")
    val bodyMetricLogId: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "metric_type")
    val metricType: BodyMetricType,

    // Which lift this estimated 1RM is for. Null for BODYWEIGHT rows.
    @ColumnInfo(name = "exercise_definition_id")
    val exerciseDefinitionId: Int? = null,

    // Bodyweight or estimated 1RM value, in whatever unit the user's
    // lbs_view setting implies — consistent with how Exercise.weight is
    // already stored unitless elsewhere in this app.
    val value: Float,

    @ColumnInfo(name = "recorded_at")
    val recordedAt: Long = System.currentTimeMillis(),

    val notes: String? = null
)
