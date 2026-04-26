package com.example.powerlifter_companion.viewmodel

data class HomeUIState(
    val userName: String = "Logan",
    val nextWorkoutName: String? = null,
    val nextWorkoutDay: String? = null,
    val weeklyExposure: Map<String, String>,
    val currentBlockName: String? = null,
)