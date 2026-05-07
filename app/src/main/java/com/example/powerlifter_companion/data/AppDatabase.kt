package com.example.powerlifter_companion.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseDefinition
import com.example.powerlifter_companion.entities.PostWorkout
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.UserSettings
import com.example.powerlifter_companion.entities.Users
import com.example.powerlifter_companion.entities.Workout
import com.example.powerlifter_companion.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Users::class,
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
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDefinitionDao(): ExerciseDefinitionDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun usersDao(): UsersDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun trainingWeekDao(): TrainingWeekDao
    abstract fun trainingBlocksDao(): TrainingBlocksDao
    abstract fun postWorkoutDao(): PostWorkoutDao
    abstract fun userSettingsDao(): UserSettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: android.content.Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "powerlifter_database"
                )
                .addCallback(AppDatabaseCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    database.exerciseDefinitionDao().insertExerciseDefinitionList(ExerciseSeedData.defaultExercises)
                }
            }
        }
    }
}