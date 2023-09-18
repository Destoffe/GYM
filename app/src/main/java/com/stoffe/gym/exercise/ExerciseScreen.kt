package com.stoffe.gym.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.GymTheme
import com.stoffe.gym.R
import com.stoffe.gym.compose.*
import com.stoffe.gym.database.entities.Exercise
import com.stoffe.gym.database.entities.ExerciseData
import com.stoffe.gym.database.entities.Workout
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    exerciseData: List<ExerciseData>?,
    currentExercise: Exercise,
    onExerciseCardClick: (Exercise) -> Unit,
    onExerciseCardLongClick: (ExerciseData) -> Unit,
    onBackArrowClick: () -> Unit,
    onFabClick: () -> Unit,
    navController: NavController,
) {

    val fabItems = listOf(
        FabItem(
            icon = Icons.Filled.FitnessCenter,
            label = stringResource(id = R.string.add_body_data),
            onFabItemClicked = onFabClick
        ) ,
    )
    if (exerciseData != null) {

        val openAlertDialog = remember { mutableStateOf(false) }
        val currentExerciseData = remember { mutableStateOf(ExerciseData(0,0,0,0f,0, LocalDate.now())) }

        GymTheme {
            Scaffold(
                floatingActionButton = {
                    MultiFloatingActionButton(
                        fabIcon = painterResource(id = R.drawable.ic_baseline_add_24),
                        items = fabItems
                    )
                },
                floatingActionButtonPosition = FabPosition.End,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(currentExercise.name) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Navigate back arrow"
                                )
                            }
                        }
                    )
                },

                ) {

                when {
                    openAlertDialog.value -> {
                        DeleteDialog(
                            onConfirmation = {
                                openAlertDialog.value = false
                                onExerciseCardLongClick(currentExerciseData.value)
                            },
                            onDismissRequest = { openAlertDialog.value = false },
                            dialogTitle = stringResource(id = R.string.delete_exercise_log),
                            dialogText = stringResource(id = R.string.delete_exercise_data_subtitle),
                            icon = Icons.Filled.Delete
                        )
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(paddingValues = it)
                ) {
                    items(exerciseData) { exerciseData ->
                        ExerciseCard(
                            setsText = stringResource(id = R.string.exercise_sets_ammount,exerciseData.sets),
                            repsText = stringResource(id = R.string.exercise_reps_ammount, exerciseData.reps),
                            weightText = stringResource(id = R.string.exercise_weight_ammount, exerciseData.weight),
                            dateText =  exerciseData.date.toString(),
                            onCardClick = {},
                            onCardLongClick = {
                                openAlertDialog.value = true
                                currentExerciseData.value = exerciseData
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun DashboardScreenPreview() {
    GymTheme {
        ExerciseScreen(
            exerciseData = listOf(),
            onExerciseCardClick = {},
            onBackArrowClick = {},
            navController = NavController(LocalContext.current),
            currentExercise = Exercise("Split",1),
            onFabClick = {},
            onExerciseCardLongClick = {}
        )
    }
}