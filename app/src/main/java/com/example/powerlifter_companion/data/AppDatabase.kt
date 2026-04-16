package com.example.powerlifter_companion.data

import androidx.room.RoomDatabase
import androidx.room3.Database
import androidx.room3.TypeConverters
import com.example.powerlifter_companion.util.Converters
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.Exercise


@Database(
    entities = [
        ExerciseDefinition::class,
    Exercise::class
    ],

    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun exerciseDefinitionDao(): ExerciseDefinitionDao
    abstract fun exerciseDao(): ExerciseDao
}