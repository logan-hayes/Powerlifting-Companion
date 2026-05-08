package com.example.powerlifter_companion.viewmodel

import android.R.attr.name
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.powerlifter_companion.data.TrainingRepository
import com.example.powerlifter_companion.entities.Exercise
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TrainingViewModel(
    private val trainingRepository: TrainingRepository
) : ViewModel() {

    //Block Specific//

    private val _selectedBlockId = MutableStateFlow<Long?>(null)
    val selectedBlockId: StateFlow<Long?> = _selectedBlockId

    private val _trainingBlockName = MutableStateFlow("")
    val trainingBlockName: StateFlow<String> = _trainingBlockName

    private val _blockLength = MutableStateFlow<Int?>(null)
    val blockLength: StateFlow<Int?> = _blockLength

    //Week Specific//

    private val _selectedTrainingWeekId = MutableStateFlow<Long?>(null)
    val selectedTrainingWeekId: StateFlow<Long?> = _selectedTrainingWeekId

    //Workout Specific//

    private val _selectedWorkoutId = MutableStateFlow<Long?>(null)
    val selectedWorkoutId: StateFlow<Long?> = _selectedWorkoutId

    private val _workoutName = MutableStateFlow("")
    val workoutName: StateFlow<String> = _workoutName

    private val _dayNumber = MutableStateFlow<Int?>(null)
    val dayNumber: StateFlow<Int?> = _dayNumber

    private val _workoutNotes = MutableStateFlow("")
    val workoutNotes: StateFlow<String> = _workoutNotes

    //Exercise Specific//

    private val _selectedExerciseId = MutableStateFlow<Long?>(null)
    val selectedExerciseId: StateFlow<Long?> = _selectedExerciseId

    private val _exerciseSets = MutableStateFlow<Int?>(null)
    val selectedSets: StateFlow<Int?> = _exerciseSets

    private val _exerciseReps = MutableStateFlow<Int?>(null)
    val exerciseReps: StateFlow<Int?> = _exerciseReps

    private val _exerciseWeight = MutableStateFlow<Int?>(null)
    val exerciseWeight: StateFlow<Int?> = _exerciseWeight

    private val _exerciseRpe = MutableStateFlow<Float?>(null)
    val exerciseRpe: StateFlow<Float?> = _exerciseRpe

    private val _exerciseNotes = MutableStateFlow<String?>("")
    val exerciseNotes: StateFlow<String?> = _exerciseNotes

    private val _exercise1RM = MutableStateFlow<Int?>(null)
    val exercise1RM: StateFlow<Int?> = _exercise1RM

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    val exercisesInWorkout =
        _selectedWorkoutId
            .filterNotNull()
            .flatMapLatest { workoutId ->
                trainingRepository.getExercisesInWorkout(workoutId)
            }
    val workoutsInTrainingWeek =
        _selectedTrainingWeekId
            .filterNotNull()
            .flatMapLatest { trainingWeekId ->
                trainingRepository.getWorkoutsInWeek(trainingWeekId)
            }
    val trainingWeeksInBlock =
        _selectedBlockId
            .filterNotNull()
            .flatMapLatest { workoutId ->
                trainingRepository.getWeeksInBlock(workoutId)
            }
    //Block Specific//

    fun selectTrainingBlock(blockId: Long) {
        _selectedBlockId.value = blockId
    }

    fun updateBlockName(name: String){
        _trainingBlockName.value = name
    }

    fun updateBlockLength(length: Int?) {
        _blockLength.value = length
    }

    fun deleteTrainingBlock(block: TrainingBlocks) {
        viewModelScope.launch {
            trainingRepository.deleteTrainingBlock(block)
        }
    }

    fun createBlock(){
        val name = _trainingBlockName.value.trim()
        val length = _blockLength.value

        if(name.isBlank()){
            _errorMessage.value = "Cannot leave name blank"
            return
        }

        if (length == null) {
            _errorMessage.value = "Select block length"
            return
        }

        viewModelScope.launch{
            trainingRepository.createTrainingBlock(
                TrainingBlocks(
                    userId = 0L,
                    blockName = name,
                    weekLength = length
                )
            )
            //resets state
            _trainingBlockName.value = ""
            _blockLength.value = null
            _errorMessage.value = null
        }
    }
    //Week Specific//

    fun selectTrainingWeek(trainingWeekId: Long) {
        _selectedTrainingWeekId.value = trainingWeekId
    }

    //Workout Specific//

    fun selectWorkout(workoutId: Long) {
        _selectedWorkoutId.value = workoutId
    }

    fun createWorkout(){
        val name = _workoutName.value.trim()
        val notes = _workoutNotes.value
        val trainingWeekId = _selectedTrainingWeekId.value

        if(name.isBlank()){
            _errorMessage.value = "Cannot leave name blank"
            return
        }

        if(trainingWeekId == null){
            _errorMessage.value = "Select a training week"
            return
        }

        viewModelScope.launch{
            trainingRepository.createWorkoutInWeek(
                Workout(
                    workoutId = 0L,
                    trainingWeekId = trainingWeekId,
                    workoutName = name,
                    dayNumber = 1,
                    notes = notes,
                    )
            )
            _workoutName.value = ""
            _workoutNotes.value = ""
            _errorMessage.value = null
        }
    }

    fun clearWorkoutInput(){
        _workoutName.value = ""
        _workoutNotes.value = ""
        _errorMessage.value = null
    }

    fun updateWorkoutName(name: String){
        _workoutName.value = name
    }

    fun updateWorkoutNotes(notes: String){
        _workoutNotes.value = notes
    }

    //Exercise Specific//

    fun addExerciseToWorkout(){
        val workoutId = _selectedWorkoutId.value
        val exerciseId = _selectedExerciseId.value
        val sets = _exerciseSets.value
        val reps = _exerciseReps.value
        val weight = _exerciseWeight.value
        val rpe = _exerciseRpe.value
        val notes = _exerciseNotes.value.orEmpty()

        if (workoutId == null) {
            _errorMessage.value = "select a workout first"
            return
        }

        if (exerciseId == null){
            _errorMessage.value = "Select an exercise first"
            return
        }

        if (reps == null || reps <= 0) {
            _errorMessage.value = "Invalid rep range"
            return
        }
        viewModelScope.launch{
            trainingRepository.addExerciseToWorkout(
            Exercise(
                workoutId = workoutId,
                exerciseDefinition = exerciseId.toInt(),
                sets = sets,
                reps = reps,
                weight = weight,
                rpe = rpe,
                notes = notes,
                targetWeight = null,
                maxPercentage = null
            ))
        }
    }

    fun clearExerciseInput(){
        _selectedWorkoutId.value = null
        _selectedExerciseId.value = null
        _exerciseSets.value = null
        _exerciseReps.value = null
        _exerciseWeight.value = null
        _exerciseRpe.value = null
        _exerciseNotes.value = ""
        _exercise1RM.value = null
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            trainingRepository.deleteExercise(exercise)
        }
    }

    fun updateSets(sets: Int?){
        _exerciseSets.value = sets
    }

    fun updateReps(reps: Int){
        _exerciseReps.value = reps
    }

    fun updateWeight(weight: Int){
        _exerciseWeight.value = weight
    }

    fun updateRpe(Rpe: Float){
        _exerciseRpe.value = Rpe
    }

    fun updateNotes(notes: String){
        _exerciseNotes.value = notes
    }

    fun update1RM(max: Int){
        _exercise1RM.value = max
    }

    fun selectExercise(exerciseId: Long) {
        _selectedExerciseId.value = exerciseId
    }

    fun clearError() {
        _errorMessage.value = null
    }
}