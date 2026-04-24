package com.example.powerlifter_companion.data

import androidx.room.RoomDatabase
import androidx.room3.Database
import androidx.room3.TypeConverters
import com.example.powerlifter_companion.util.Converters
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.Users
import com.example.powerlifter_companion.entities.UserSettings
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Workout
import com.example.powerlifter_companion.entities.PostWorkout



@Database(
    entities = [
        Users:: class,
        UserSettings::class,
        TrainingBlocks::class,
        TrainingWeek::class,
        Workout::class,
        PostWorkout::class,
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
    abstract fun UsersDao(): UsersDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun trainingWorkDao(): TrainingWeekDao
    abstract fun trainingBlocksDao(): TrainingBlocksDao
}