package com.example.powerlifter_companion.util

import androidx.room3.TypeConverter
import com.example.powerlifter_companion.entities.ExerciseCategory
import com.example.powerlifter_companion.entities.MuscleGroup


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
}