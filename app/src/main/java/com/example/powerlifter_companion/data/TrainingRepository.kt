package com.example.powerlifter_companion.data

import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseLog
import com.example.powerlifter_companion.entities.ExerciseLogStatus
import com.example.powerlifter_companion.entities.PostWorkout
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Workout

// Input shape for completing a workout: one entry per exercise, carrying
// what the user actually did (or that they skipped it). Kept in the data
// layer so both the ViewModel and Repository share the same type.
data class ExerciseLogInput(
    val exerciseId: Int,
    val status: ExerciseLogStatus,
    val actualSets: Int?,
    val actualReps: Int?,
    val actualWeight: Int?,
    val actualRpe: Float?
)

class TrainingRepository(
    private val trainingBlocksDao: TrainingBlocksDao,
    private val trainingWeekDao: TrainingWeekDao,
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao,
    private val exerciseDefinitionDao: ExerciseDefinitionDao,
    private val postWorkoutDao: PostWorkoutDao,
    private val exerciseLogDao: ExerciseLogDao
) {

    // Block specific
    fun getAllTrainingBlocks() =
        trainingBlocksDao.getAllTrainingBlocks()

    fun getAllBlocks() =
        trainingBlocksDao.getAllBlocks()

    fun getBlockById(trainingBlockId: Long) =
        trainingBlocksDao.getTrainingBlock(trainingBlockId)

    suspend fun updateTrainingBlock(trainingBlock: TrainingBlocks) =
        trainingBlocksDao.updateTrainingBlock(trainingBlock)

    suspend fun deleteTrainingBlock(trainingBlock: TrainingBlocks) =
        trainingBlocksDao.deleteTrainingBlock(trainingBlock)

    // Creates new trainingBlock and associated Weeks
    suspend fun createTrainingBlock(trainingBlock: TrainingBlocks) {
        val blockId = trainingBlocksDao.insertTrainingBlock(trainingBlock)

        for (weekNumber in 1..trainingBlock.weekLength) {
            trainingWeekDao.insertTrainingWeek(
                TrainingWeek(
                    blockId = blockId,
                    weekNumber = weekNumber
                )
            )
        }
    }

    // Week specific
    fun getWeeksInBlock(blockId: Long) =
        trainingWeekDao.getAllTrainingWeeksInBlock(blockId)

    suspend fun getWeekById(trainingWeekId: Long) =
        trainingWeekDao.getTrainingWeek(trainingWeekId)

    suspend fun updateTrainingWeek(trainingWeek: TrainingWeek) =
        trainingWeekDao.updateTrainingWeek(trainingWeek)

    suspend fun deleteTrainingWeek(trainingWeek: TrainingWeek) =
        trainingWeekDao.deleteTrainingWeek(trainingWeek)

    // Workout specific
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

    // Exercise Specific
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

    fun getAllExerciseDefinitions() =
        exerciseDefinitionDao.getAllExerciseDefinitions()

    suspend fun seedExercisesIfEmpty() {
        if (exerciseDefinitionDao.getExerciseDefinitionCount() == 0) {
            exerciseDefinitionDao.insertExerciseDefinitionList(
                ExerciseSeedData.defaultExercises
            )
        }
    }

    // Block overview (continuous-scroll redesign): block-scoped reads so the
    // ViewModel can build one nested Week -> Workout -> Exercise structure
    // instead of requiring a week/workout to be selected first.
    fun getWorkoutsInBlock(blockId: Long) =
        workoutDao.getAllWorkoutsInBlock(blockId)

    fun getExercisesInBlock(blockId: Long) =
        exerciseDao.getAllExercisesInBlock(blockId)

    // Today's Workout completion flow: records one PostWorkout (workout-level
    // summary) plus one ExerciseLog per exercise (what actually happened).
    fun getPostWorkoutForWorkout(workoutId: Long) =
        postWorkoutDao.getPostWorkoutByWorkoutId(workoutId)

    fun getPostWorkoutsInBlock(blockId: Long) =
        postWorkoutDao.getPostWorkoutsInBlock(blockId)

    suspend fun recordWorkoutCompletion(
        workoutId: Long,
        userId: Long,
        exerciseLogs: List<ExerciseLogInput>
    ) {
        // Overwrite any previous log for this workout rather than stacking a
        // duplicate PostWorkout row (its ExerciseLog children cascade-delete).
        postWorkoutDao.deletePostWorkoutForWorkout(workoutId)

        val allAsPlanned = exerciseLogs.all { it.status == ExerciseLogStatus.COMPLETED_AS_PLANNED }

        val postWorkoutId = postWorkoutDao.insertPostWorkout(
            PostWorkout(
                workoutId = workoutId,
                userId = userId,
                completedTimeStamp = System.currentTimeMillis(),
                completedAsPlanned = allAsPlanned
            )
        )

        exerciseLogDao.insertExerciseLogs(
            exerciseLogs.map { input ->
                ExerciseLog(
                    postWorkoutId = postWorkoutId.toInt(),
                    exerciseId = input.exerciseId,
                    status = input.status,
                    actualSets = input.actualSets,
                    actualReps = input.actualReps,
                    actualWeight = input.actualWeight,
                    actualRpe = input.actualRpe
                )
            }
        )
    }
}
