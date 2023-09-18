package com.stoffe.gym.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.GymTheme
import com.stoffe.gym.R
import com.stoffe.gym.compose.DeleteDialog
import com.stoffe.gym.compose.FabItem
import com.stoffe.gym.compose.GymCard
import com.stoffe.gym.compose.MultiFloatingActionButton
import com.stoffe.gym.dashboard.bmi.BmiCard
import com.stoffe.gym.database.entities.BMI
import com.stoffe.gym.database.entities.Workout
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    workouts: List<Workout>?,
    bmi: List<BMI?>?,
    onWorkoutCardClick: (Workout) -> Unit,
    onWorkoutCardLongClick: (Workout) -> Unit,
    onWorkoutStartClick: (Workout) -> Unit,
    onBmiClick: () -> Unit,
    onCreateWorkout: () -> Unit,
    onCreateBmi: () -> Unit,
    navController: NavController,
) {

    val fabItems = listOf(
        FabItem(
            icon = Icons.Filled.FitnessCenter,
            label = stringResource(id = R.string.add_workout),
            onFabItemClicked = onCreateWorkout
        ),
        FabItem(
            icon = Icons.Filled.PersonAdd,
            label = stringResource(id = R.string.fab_add_bmi),
            onFabItemClicked = onCreateBmi
        )
    )
    if (workouts != null) {
        val openAlertDialog = remember { mutableStateOf(false) }
        val currentWorkout = remember { mutableStateOf(Workout("Test")) }
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
                        title = { Text("Start page") },
                    )
                },

                ) {

                when {
                    openAlertDialog.value -> {
                        DeleteDialog(
                            onConfirmation = {
                                openAlertDialog.value = false
                                onWorkoutCardLongClick(currentWorkout.value)
                            },
                            onDismissRequest = { openAlertDialog.value = false },
                            dialogTitle = stringResource(id = R.string.delete_workout),
                            dialogText = stringResource(id = R.string.delete_workout_subtitle),
                            icon = Icons.Filled.Delete
                        )
                    }
                }
                Column() {
                    if (bmi != null && bmi.isNotEmpty()) {
                        bmi[0]?.let { it1 ->
                            BmiCard(
                                bmi = it1,
                                modifier = Modifier.padding(paddingValues = it),
                                onBmiClick = onBmiClick,
                            )
                        }
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(paddingValues = it)
                    ) {
                        items(workouts) { workout ->
                            GymCard(
                                cardTitle = workout.name,
                                subTitle = if (workout.lastTime != null) workout.lastTime.toString() else null,
                                onCardClick = { onWorkoutCardClick(workout) },
                                onCardLongClick = {
                                    openAlertDialog.value = true
                                    currentWorkout.value = workout
                                                  },
                                onFirstIconClick = { onWorkoutStartClick(workout) },
                                onSecondIconClick = { /*TODO*/ },
                                iconOne = if (workout.isActive) R.drawable.ic_stop else R.drawable.ic_play,
                                iconTwo = null,
                                isActive = workout.isActive
                            )
                        }
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
        DashboardScreen(
            workouts = listOf(Workout("Test")),
            onWorkoutCardClick = {},
            onWorkoutCardLongClick = {},
            onWorkoutStartClick = {},
            onBmiClick = {},
            navController = NavController(LocalContext.current),
            bmi = listOf(BMI(1, 75f, 175f, 1, LocalDateTime.now(), 1)),
            onCreateWorkout = {},
            onCreateBmi = {}
        )
    }
}