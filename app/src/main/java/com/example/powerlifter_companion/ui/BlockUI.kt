package com.example.powerlifter_companion.ui

import android.R.attr.name
import com.example.powerlifter_companion.entities.Exercise
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.powerlifter_companion.ui.theme.BackgroundGray
import com.example.powerlifter_companion.ui.theme.PrimaryRed
import com.example.powerlifter_companion.viewmodel.TrainingViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.clickable
import com.example.powerlifter_companion.entities.TrainingBlocks
import com.example.powerlifter_companion.entities.TrainingWeek
import com.example.powerlifter_companion.entities.Workout


@Composable
fun blockUi(
    trainingViewModel: TrainingViewModel
) {
    val blocks by trainingViewModel.trainingBlocks.collectAsState(initial = emptyList())
    val blockName by trainingViewModel.trainingBlockName.collectAsState()
    val blockLength by trainingViewModel.blockLength.collectAsState()
    val weeks by trainingViewModel.trainingWeeksInBlock.collectAsState(initial = emptyList())
    val workouts by trainingViewModel.workoutsInTrainingWeek.collectAsState(initial = emptyList())
    val workoutName by trainingViewModel.workoutName.collectAsState()
    val workoutNotes by trainingViewModel.workoutNotes.collectAsState()
    val selectedBlockId by trainingViewModel.selectedBlockId.collectAsState()
    val selectedWeekId by trainingViewModel.selectedTrainingWeekId.collectAsState()
    val selectedWorkoutId by trainingViewModel.selectedWorkoutId.collectAsState()
    val exercises by trainingViewModel.exercisesInWorkout.collectAsState(initial = emptyList())
    val selectedExerciseId by trainingViewModel.selectedExerciseId.collectAsState()
    val sets by trainingViewModel.selectedSets.collectAsState()
    val reps by trainingViewModel.exerciseReps.collectAsState()
    val weight by trainingViewModel.exerciseWeight.collectAsState()
    val rpe by trainingViewModel.exerciseRpe.collectAsState()
    val exerciseNotes by trainingViewModel.exerciseNotes.collectAsState()
    val isAddingWorkout by trainingViewModel.isAddingWorkout.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(BackgroundGray, PrimaryRed)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        if (selectedBlockId == null) {
            BlockListSection(
                blocks = blocks,
                blockName = blockName,
                blockLength = blockLength,
                onBlockNameChange = { trainingViewModel.updateBlockName(it) },
                onBlockLengthChange = { trainingViewModel.updateBlockLength(it) },
                onCreateBlock = { trainingViewModel.createBlock() },
                onBlockSelected = { trainingViewModel.selectTrainingBlock(it) }
            )
        } else {
            if (selectedWorkoutId == null) {
                SelectedBlockSection(
                    weeks = weeks,
                    workouts = workouts,
                    workoutName = workoutName,
                    workoutNotes = workoutNotes,
                    selectedWeekId = selectedWeekId,
                    isAddingWorkout = isAddingWorkout,
                    onBackClick = { trainingViewModel.clearSelectedBlock() },
                    onWeekSelected = { trainingViewModel.selectTrainingWeek(it) },
                    onWorkoutSelected = { trainingViewModel.selectWorkout(it) },
                    onWorkoutNumberSelected = {
                        trainingViewModel.selectWorkoutNumberInWeek(it)
                    },
                    onAddWorkoutClick = {
                        trainingViewModel.selectWorkoutNumberInWeek(workouts.size + 1)
                        trainingViewModel.startAddingWorkout()
                    },
                    onCancelAddWorkout = {
                        trainingViewModel.cancelAddingWorkout()
                    },
                    onWorkoutNameChange = { trainingViewModel.updateWorkoutName(it) },
                    onWorkoutNotesChange = { trainingViewModel.updateWorkoutNotes(it) },
                    onCreateWorkout = { trainingViewModel.createWorkout() }
                )
            } else {
                WorkoutDetailSection(
                    exercises = exercises,
                    selectedExerciseId = selectedExerciseId,
                    sets = sets,
                    reps = reps,
                    weight = weight,
                    rpe = rpe,
                    notes = exerciseNotes.orEmpty(),
                    onBackClick = { trainingViewModel.clearSelectedWorkout() },
                    onExerciseSelected = { trainingViewModel.selectExercise(it) },
                    onSetsChange = { trainingViewModel.updateSets(it) },
                    onRepsChange = { trainingViewModel.updateReps(it) },
                    onWeightChange = { trainingViewModel.updateWeight(it) },
                    onRpeChange = { trainingViewModel.updateRpe(it) },
                    onNotesChange = { trainingViewModel.updateNotes(it) },
                    onAddExercise = { trainingViewModel.addExerciseToWorkout() }
                )
            }
        }
    }
}

@Composable
fun BlockListSection(
    blocks: List<TrainingBlocks>,
    blockName: String,
    blockLength: Int?,
    onBlockNameChange: (String) -> Unit,
    onBlockLengthChange: (Int) -> Unit,
    onCreateBlock: () -> Unit,
    onBlockSelected: (Long) -> Unit
) {
    Text(
        text = "Select a block or create a new one",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White.copy(alpha = 0.75f)
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = blockName,
        onValueChange = onBlockNameChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Block name") },
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Block Length",
        color = Color.White,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(4, 6, 8, 12).forEach { weeks ->
            Button(
                onClick = { onBlockLengthChange(weeks) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (blockLength == weeks) PrimaryRed
                        else Color.DarkGray
                )
            ) {
                Text("$weeks")
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Button(
        onClick = onCreateBlock,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text("Create Training Block")
    }

    Spacer(modifier = Modifier.height(20.dp))

    blocks.forEach { block ->
        BlockCard(
            blockName = block.blockName,
            lengthWeeks = "${block.weekLength} weeks",
            startDate = block.startDate.toString(),
            onClick = { onBlockSelected(block.blockId) }
        )
    }
}

@Composable
fun SelectedBlockSection(
    weeks: List<TrainingWeek>,
    workouts: List<Workout>,
    workoutName: String,
    workoutNotes: String,
    selectedWeekId: Long?,
    isAddingWorkout: Boolean,
    onBackClick: () -> Unit,
    onWeekSelected: (Long) -> Unit,
    onWorkoutSelected: (Long) -> Unit,
    onWorkoutNumberSelected: (Int) -> Unit,
    onAddWorkoutClick: () -> Unit,
    onCancelAddWorkout: () -> Unit,
    onWorkoutNameChange: (String) -> Unit,
    onWorkoutNotesChange: (String) -> Unit,
    onCreateWorkout: () -> Unit
) {
    Button(
        onClick = onBackClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            contentColor = Color.White
        )
    ) {
        Text("Back to Blocks")
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "Select Week",
        color = Color.White,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    weeks.forEach { week ->
        WeekCard(
            week = week,
            isSelected = selectedWeekId == week.trainingWeekId,
            onClick = { onWeekSelected(week.trainingWeekId) }
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = if (selectedWeekId == null) "Choose a week to view workouts" else "Workouts",
        color = Color.White,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    WeekWorkoutList(
        workouts = workouts,
        onAddWorkoutClick = onAddWorkoutClick,
        onWorkoutSelected = onWorkoutSelected
    )

    if (isAddingWorkout) {
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = workoutName,
            onValueChange = onWorkoutNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Workout name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = workoutNotes,
            onValueChange = onWorkoutNotesChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Workout notes") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCreateWorkout,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryRed,
                contentColor = Color.White
            )
        ) {
            Text("Save Workout")
        }

        Button(
            onClick = onCancelAddWorkout,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text("Cancel")
        }
    }
}

@Composable
fun WeekCard(
    week: TrainingWeek,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PrimaryRed else Color(0xFF1E1E1E).copy(alpha = 0.92f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Week ${week.weekNumber}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun WorkoutCard(
    workout: Workout,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {onClick()},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151515).copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = workout.workoutName,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (workout.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = workout.notes,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun WorkoutDetailSection(
    exercises: List<Exercise>,
    selectedExerciseId: Long?,
    sets: Int?,
    reps: Int?,
    weight: Int?,
    rpe: Float?,
    notes: String,
    onBackClick: () -> Unit,
    onExerciseSelected: (Long) -> Unit,
    onSetsChange: (Int?) -> Unit,
    onRepsChange: (Int?) -> Unit,
    onWeightChange: (Int?) -> Unit,
    onRpeChange: (Float?) -> Unit,
    onNotesChange: (String) -> Unit,
    onAddExercise: () -> Unit
) {
    Button(
        onClick = onBackClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            contentColor = Color.White
        )
    ) {
        Text("Back to Week")
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "Workout Details",
        color = Color.White,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(12.dp))

    BlockTableHeader()

    Spacer(modifier = Modifier.height(8.dp))

    exercises.forEach { exercise ->
        BlockTableRow(
            exercise = exercise.exerciseDefinition.toString(),
            sets = exercise.sets?.toString() ?: "",
            reps = exercise.reps?.toString() ?: "",
            weight = exercise.weight?.toString() ?: "",
            rpe = exercise.rpe?.toString() ?: "",
            notes = exercise.notes ?: ""
        )
    }
    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "Add Exercise",
        color = Color.White,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    Button(
        onClick = { onExerciseSelected(1L) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedExerciseId == 1L) PrimaryRed else Color.DarkGray,
            contentColor = Color.White
        )
    ) {
        Text(if (selectedExerciseId == 1L) "Squat Selected" else "Select Squat")
    }

    OutlinedTextField(
        value = sets?.toString() ?: "",
        onValueChange = { onSetsChange(it.toIntOrNull()) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Sets") },
        singleLine = true
    )

    OutlinedTextField(
        value = reps?.toString() ?: "",
        onValueChange = { onRepsChange(it.toIntOrNull()) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Reps") },
        singleLine = true
    )

    OutlinedTextField(
        value = weight?.toString() ?: "",
        onValueChange = { onWeightChange(it.toIntOrNull()) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Weight") },
        singleLine = true
    )

    OutlinedTextField(
        value = rpe?.toString() ?: "",
        onValueChange = { onRpeChange(it.toFloatOrNull()) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("RPE") },
        singleLine = true
    )

    OutlinedTextField(
        value = notes,
        onValueChange = onNotesChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Notes") }
    )

    Spacer(modifier = Modifier.height(12.dp))

    Button(
        onClick = onAddExercise,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryRed,
            contentColor = Color.White
        )
    ) {
        Text("Add Exercise")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun blockHeader(){
    TopAppBar(
        title = {
            Text(
                text = "Training Blocks",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun WeekWorkoutList(
    workouts: List<Workout>,
    onAddWorkoutClick: () -> Unit,
    onWorkoutSelected: (Long) -> Unit
) {
    Column {
        Text(
            text = "Workouts This Week",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        workouts.sortedBy { it.dayNumber }.forEachIndexed { index, workout ->
            WorkoutCard(
                workout = workout.copy(dayNumber = index + 1),
                onClick = { onWorkoutSelected(workout.workoutId) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onAddWorkoutClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryRed,
                contentColor = Color.White
            )
        ) {
            Text("+ Add Workout")
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false)
{
    Box(
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart)
    {
        Text(
            text = text,
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
            color = if (isHeader) Color.Black else Color.White,
            style = if(isHeader)
                MaterialTheme.typography.bodyMedium
            else
                MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun BlockTableHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.88f))
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        TableCell("Exercise", weight = 2f, isHeader = true)
        TableCell("Sets", weight = 1f, isHeader = true)
        TableCell("Reps", weight = 1f, isHeader = true)
        TableCell("Weight", weight = 1f, isHeader = true)
        TableCell("RPE", weight = 1f, isHeader = true)
        TableCell("Notes", weight = 2f, isHeader = true)

    }
}

@Composable
fun currentBlock(
    blockName: String,
    lengthWeeks: String,
    StartDate: String
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.08f)))
    {
        Column(
            modifier = Modifier.padding(20.dp))
        {
            Text(
                text = blockName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = lengthWeeks,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f)
            )

            Text(
                text = "startDate",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

        }
    }
}
@Composable
fun BlockCard(
    blockName: String,
    lengthWeeks: String,
    startDate: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable{ onClick() }
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .height(96.dp)
                .background(PrimaryRed)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E).copy(alpha = 0.92f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    text = blockName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = lengthWeeks,
                    color = Color.White.copy(alpha = 0.78f)
                )

                Text(
                    text = startDate,
                    color = Color.White.copy(alpha = 0.62f)
                )
            }
        }
    }
}