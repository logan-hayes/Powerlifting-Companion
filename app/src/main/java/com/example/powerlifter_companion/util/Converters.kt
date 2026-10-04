package com.example.powerlifter_companion.util

import androidx.room.TypeConverter
import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.MuscleGroup
import com.example.powerlifter_companion.entities.ExerciseLogStatus
import com.example.powerlifter_companion.entities.BodyMetricType


/**
* @author Logan Hayes
*
* Coverter class for turning java enum fields into String format
 * for room database
* */
class Converters {

    @TypeConverter
    fun fromExerciseCategory(value: ExerciseCategory):  String{
        return value.name
    }
    @TypeConverter
    fun toExerciseCategory(value: String):  ExerciseCategory{
        return ExerciseCategory.valueOf(value)
}
    @TypeConverter
    fun fromMuscleGroup(value: MuscleGroup): String {
        return value.name
    }

    @TypeConverter
    fun toMuscleGroup(value: String): MuscleGroup{
        return MuscleGroup.valueOf(value)
    }

    @TypeConverter
    fun fromExerciseLogStatus(value: ExerciseLogStatus): String {
        return value.name
    }

    @TypeConverter
    fun toExerciseLogStatus(value: String): ExerciseLogStatus {
        return ExerciseLogStatus.valueOf(value)
    }

    @TypeConverter
    fun fromBodyMetricType(value: BodyMetricType): String {
        return value.name
    }

    @TypeConverter
    fun toBodyMetricType(value: String): BodyMetricType {
        return BodyMetricType.valueOf(value)
    }
}
