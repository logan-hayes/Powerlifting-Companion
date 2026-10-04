package com.example.powerlifter_companion.entities

/**
 * Discriminator for BodyMetricLog rows. Bodyweight has one history per user;
 * estimated 1RM has one history per lift (via exerciseDefinitionId), so a
 * lifter tracking squat/bench/deadlift gets three independent series.
 */
enum class BodyMetricType {
    BODYWEIGHT,
    ESTIMATED_ONE_REP_MAX
}
