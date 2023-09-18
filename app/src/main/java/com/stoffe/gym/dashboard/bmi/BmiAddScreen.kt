package com.stoffe.gym.dashboard.bmi

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
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.compose.GymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiAddAScreen(
    onCreateBmi: (Float, Float, Int, Int) -> Unit,
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
        ) { paddingValues ->

            val possibleValues = listOf("Male", "Female", "Other", "Do not want to specify")

            var weight by remember { mutableStateOf(TextFieldValue()) }
            var height by remember { mutableStateOf(TextFieldValue()) }
            var age by remember { mutableStateOf(0) }
            var gender by remember { mutableStateOf(possibleValues[0]) }

            val weightIsInvalidInput by rememberUpdatedState(
                weight.text.isEmpty() || weight.text.toFloatOrNull() == null
            )
            val heightIsInvalidInput by rememberUpdatedState(
                height.text.isEmpty() || height.text.toFloatOrNull() == null
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                TextField(
                    value = weight,
                    onValueChange = { newText -> weight = newText },
                    label = { Text(stringResource(id = com.stoffe.gym.R.string.hint_weight)) },
                    isError = weightIsInvalidInput
                )
                TextField(
                    value = height,
                    onValueChange = { newText -> height = newText },
                    label = { Text(stringResource(id = com.stoffe.gym.R.string.hint_height)) },
                    isError = heightIsInvalidInput
                )
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 64.dp) ) {
                    Text(text = "Age")
                    Card {
                        NumberPicker(
                            modifier = Modifier.fillMaxWidth(),
                            value = age,
                            onValueChange = { number -> age = number },
                            range = 10..100,
                        )
                    }

                }
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 64.dp)) {
                    Text(text = "Gender")
                    Card{
                        ListItemPicker(
                            modifier = Modifier.fillMaxWidth(),
                            label = { it },
                            value = gender,
                            onValueChange = { gender = it },
                            list = possibleValues
                        )
                    }

                }

                Button(onClick = {
                    onCreateBmi(
                        weight.text.toFloat(),
                        height.text.toFloat(),
                        age,
                        if (gender == possibleValues[0]) 0 else if (gender == possibleValues[1]) 1 else if (gender == possibleValues[2]) 2 else 3
                    )
                },
                    enabled = weightIsInvalidInput.not() && heightIsInvalidInput.not()
                    ) {
                    Text(stringResource(id = com.stoffe.gym.R.string.add_bmi))
                }
            }
        }
    }
}

@Preview
@Composable
fun WorkoutAddScreenPreview() {
    GymTheme {
        BmiAddAScreen(
            onCreateBmi = { _, _, _, _ -> },
            onExit = {}
        )
    }
}