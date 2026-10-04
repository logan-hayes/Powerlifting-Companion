package com.example.powerlifter_companion.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.example.powerlifter_companion.util.Converters
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.Users
import com.example.powerlifter_companion.entities.UserSettings
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Workout
import com.example.powerlifter_companion.entities.PostWorkout
import com.example.powerlifter_companion.entities.ExerciseLog
import com.example.powerlifter_companion.entities.BodyMetricLog

@Database(
    entities = [
        Users::class,
        UserSettings::class,
        TrainingBlocks::class,
        TrainingWeek::class,
        Workout::class,
        PostWorkout::class,
        ExerciseDefinition::class,
        Exercise::class,
        ExerciseLog::class,
        BodyMetricLog::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDefinitionDao(): ExerciseDefinitionDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun usersDao(): UsersDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun trainingWeekDao(): TrainingWeekDao
    abstract fun trainingBlocksDao(): TrainingBlocksDao
    abstract fun userSettingsDao(): UserSettingsDao
    abstract fun postWorkoutDao(): PostWorkoutDao
    abstract fun exerciseLogDao(): ExerciseLogDao
    abstract fun bodyMetricLogDao(): BodyMetricLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "powerlifter_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
