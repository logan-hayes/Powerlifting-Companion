package com.example.powerlifter_companion.data

import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Workout


class TrainingRepository(
    private val trainingBlocksDao: TrainingBlocksDao,
    private val trainingWeekDao: TrainingWeekDao,
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao
){

    //Block specific//

    fun getAllTrainingBlocks() =
        trainingBlocksDao.getAllTrainingBlocks()

    fun getBlockById(trainingBlockId: Long) =
        trainingBlocksDao.getTrainingBlock(trainingBlockId)

    suspend fun updateTrainingBlock(trainingBlock: TrainingBlocks) =
        trainingBlocksDao.updateTrainingBlock(trainingBlock)

    suspend fun deleteTrainingBlock(trainingBlock: TrainingBlocks) =
        trainingBlocksDao.deleteTrainingBlock(trainingBlock)

    //Creates new trainingBlock and associated Weeks
    suspend fun createTrainingBlock(trainingBlock: TrainingBlocks) {
        val blockId = trainingBlocksDao.insertTrainingBlock(trainingBlock)

        for (weekNumber in 1..trainingBlock.weekLength) {
            trainingWeekDao.insertTrainingWeek(
                TrainingWeek(
                    blockId = blockId,
                    weekNumber = weekNumber))
        }
    }

    //Week specific//

    fun getWeeksInBlock(blockId: Long) =
        trainingWeekDao.getAllTrainingWeeksInBlock(blockId)

    fun getWeekById(trainingWeekId: Long) =
        trainingWeekDao.getTrainingWeek(trainingWeekId)

    suspend fun updateTrainingWeek(trainingWeek: TrainingWeek) =
        trainingWeekDao.updateTrainingWeek(trainingWeek)

    suspend fun deleteTrainingWeek(trainingWeek: TrainingWeek) =
        trainingWeekDao.deleteTrainingWeek(trainingWeek)

    //Workout specific//

    fun getWorkoutsInWeek(trainingWeekId: Long) =
        workoutDao.getAllWorkoutsInTrainingWeek(trainingWeekId)

    fun getWorkoutById(workoutId: Long) =
        workoutDao.getWorkoutById(workoutId)

    suspend fun updateWorkout(workout: Workout) =
        workoutDao.updateWorkout(workout)

    suspend fun deleteWorkout(workout: Workout) =
        workoutDao.deleteWorkout(workout)

    suspend fun createWorkoutInWeek(workout: Workout) =
        workoutDao.insertWorkout(workout)

    //Exercise Specific//

    suspend fun addExerciseToWorkout(exercise: Exercise) =
        exerciseDao.insertExercise(exercise)

    fun getExercisesInWorkout(workoutId: Long) =
        exerciseDao.getAllExercisesInWorkout(workoutId)

    fun getExerciseById(exerciseId: Long) =
        exerciseDao.getExerciseById(exerciseId)

    suspend fun updateExercise(exercise: Exercise) =
        exerciseDao.updateExercise(exercise)

    suspend fun deleteExercise(exercise: Exercise) =
        exerciseDao.deleteExercise(exercise)
    }