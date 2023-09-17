package com.stoffe.gym.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.GymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDataAddScreen(
    onCreateExercise: (Int,Int,Float) -> Unit,
    onExit: () -> Unit,
) {
    GymTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(id = com.stoffe.gym.R.string.add_body_data)) },
                    navigationIcon = {
                        IconButton(onClick = onExit) {
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "Navigate back arrow"
                            )
                        }
                    }
                )
            },
        ) {
            var sets by remember { mutableStateOf(TextFieldValue()) }
            var reps by remember { mutableStateOf(TextFieldValue()) }
            var weight by remember { mutableStateOf(TextFieldValue()) }

            val setsIsInvalidInput by rememberUpdatedState(
                sets.text.isEmpty() || sets.text.toIntOrNull() == null
            )
            val repsIsInvalidInput by rememberUpdatedState(
                reps.text.isEmpty() || reps.text.toIntOrNull() == null
            )
            val weightIsInvalidInput by rememberUpdatedState(
                weight.text.isEmpty() || weight.text.toFloatOrNull() == null
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                TextField(
                    value = sets,
                    onValueChange = { newText -> sets = newText },
                    label = { Text(stringResource(id = com.stoffe.gym.R.string.sets)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = setsIsInvalidInput
                )
                TextField(
                    value = reps,
                    onValueChange = { newText -> reps = newText },
                    label = { Text(stringResource(id = com.stoffe.gym.R.string.reps)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = repsIsInvalidInput
                )
                TextField(
                    value = weight,
                    onValueChange = { newText -> weight = newText },
                    label = { Text(stringResource(id = com.stoffe.gym.R.string.weight)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = weightIsInvalidInput
                )

                Button(
                    onClick = { onCreateExercise(sets.text.toInt(),reps.text.toInt(),weight.text.toFloat()) },
                    enabled = setsIsInvalidInput.not() && repsIsInvalidInput.not() && weightIsInvalidInput.not()
                ) {
                    Text(stringResource(id = com.stoffe.gym.R.string.add_exercise))
                }
            }
        }
    }
}

@Preview
@Composable
fun ExerciseAddScreenPreview() {
    GymTheme {
        ExerciseDataAddScreen(
            onCreateExercise = { _,_,_ ->},
            onExit = {}
        )
    }
}