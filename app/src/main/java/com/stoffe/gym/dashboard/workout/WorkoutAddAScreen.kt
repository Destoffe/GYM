package com.stoffe.gym.dashboard.workout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.GymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutAddScreen(
    onCreateWorkout: (String) -> Unit,
    onExit: () -> Unit,
) {
    GymTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(id = com.stoffe.gym.R.string.add_workout)) },
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
            var text by remember { mutableStateOf(TextFieldValue()) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                TextField(
                    value = text,
                    onValueChange = { newText -> text = newText },
                    label = { Text(stringResource(id = com.stoffe.gym.R.string.hint_workout_name)) },
                )

                Button(onClick = { onCreateWorkout(text.text) }) {
                    Text(stringResource(id = com.stoffe.gym.R.string.add_workout))
                }
            }
        }
    }
}

@Preview
@Composable
fun WorkoutAddScreenPreview() {
    GymTheme {
        WorkoutAddScreen(
            onCreateWorkout = {},
            onExit = {}
        )
    }
}