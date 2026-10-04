package com.example.powerlifter_companion.data

import com.example.powerlifter_companion.entities.BodyMetricLog
import com.example.powerlifter_companion.entities.BodyMetricType
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.ExerciseLog
import com.example.powerlifter_companion.entities.ExerciseLogStatus
import com.example.powerlifter_companion.entities.PostWorkout
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Users
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
    private val exerciseLogDao: ExerciseLogDao,
    private val usersDao: UsersDao,
    private val bodyMetricLogDao: BodyMetricLogDao
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

    // The whole app hardcodes userId = 1L (block creation, workout
    // completion, ...) but nothing ever created that row, so any insert with
    // an enforced foreign key to users (PostWorkout) throws a
    // SQLiteConstraintException the first time it actually runs. Seed it the
    // same way the exercise definitions are seeded.
    suspend fun seedDefaultUserIfMissing() {
        if (usersDao.getUserById(1L) == null) {
            usersDao.insertUser(Users(userId = 1L, name = "Default User", email = null))
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
        exerciseLogs: List<ExerciseLogInput>,
        overallStatus: ExerciseLogStatus
    ) {
        // Overwrite any previous log for this workout rather than stacking a
        // duplicate PostWorkout row (its ExerciseLog children cascade-delete).
        postWorkoutDao.deletePostWorkoutForWorkout(workoutId)

        // Passed in explicitly rather than derived from exerciseLogs.all { ... },
        // since that was vacuously true for a workout with zero exercises —
        // every empty-exercise completion silently recorded as "as planned."
        val completedAsPlanned = overallStatus == ExerciseLogStatus.COMPLETED_AS_PLANNED

        val postWorkoutId = postWorkoutDao.insertPostWorkout(
            PostWorkout(
                workoutId = workoutId,
                userId = userId,
                completedTimeStamp = System.currentTimeMillis(),
                completedAsPlanned = completedAsPlanned
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

    // Body metric history (bodyweight + per-lift estimated 1RM). Each call
    // adds a new row rather than overwriting — that's the whole point versus
    // the old single-value approach.
    suspend fun logBodyweight(userId: Long, value: Float, notes: String? = null) {
        bodyMetricLogDao.insertBodyMetricLog(
            BodyMetricLog(
                userId = userId,
                metricType = BodyMetricType.BODYWEIGHT,
                exerciseDefinitionId = null,
                value = value,
                notes = notes
            )
        )
    }

    suspend fun logEstimatedOneRepMax(
        userId: Long,
        exerciseDefinitionId: Int,
        value: Float,
        notes: String? = null
    ) {
        bodyMetricLogDao.insertBodyMetricLog(
            BodyMetricLog(
                userId = userId,
                metricType = BodyMetricType.ESTIMATED_ONE_REP_MAX,
                exerciseDefinitionId = exerciseDefinitionId,
                value = value,
                notes = notes
            )
        )
    }

    fun getBodyweightHistory(userId: Long) =
        bodyMetricLogDao.getMetricHistory(userId, BodyMetricType.BODYWEIGHT)

    fun getOneRepMaxHistory(userId: Long, exerciseDefinitionId: Int) =
        bodyMetricLogDao.getOneRepMaxHistory(userId, exerciseDefinitionId)

    suspend fun getLatestBodyweight(userId: Long) =
        bodyMetricLogDao.getLatestMetric(userId, BodyMetricType.BODYWEIGHT)

    suspend fun getLatestOneRepMax(userId: Long, exerciseDefinitionId: Int) =
        bodyMetricLogDao.getLatestOneRepMax(userId, exerciseDefinitionId)
}
