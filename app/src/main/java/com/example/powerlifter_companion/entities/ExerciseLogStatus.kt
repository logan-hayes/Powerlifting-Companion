package com.example.powerlifter_companion.entities

/**
 * Status recorded for a single exercise when a workout is completed.
 * Mirrors the requirement: after a workout, log "completed as planned" or
 * "adjust" (which covers modified and skipped), at exercise granularity —
 * no per-set logging.
 */
enum class ExerciseLogStatus {
    COMPLETED_AS_PLANNED,
    MODIFIED,
    SKIPPED
}
