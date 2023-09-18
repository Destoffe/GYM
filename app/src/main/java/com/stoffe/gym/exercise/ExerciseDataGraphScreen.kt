package com.stoffe.gym.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import com.example.compose.GymTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.stoffe.gym.R
import com.stoffe.gym.database.entities.ExerciseData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDataGraphScreen(
    data: List<ExerciseData>,
    onExit: () -> Unit,
) {
    GymTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(id = R.string.graph_label)) },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var state  by remember { mutableStateOf(0) }
                
                TabRow(selectedTabIndex = state) {
                    Tab(
                        text = { Text(stringResource(id = R.string.sets)) },
                        selected = state == 0,
                        onClick = { state = 0 }
                    )
                    Tab(
                        text = { Text(stringResource(id = R.string.reps)) },
                        selected = state == 1,
                        onClick = { state = 1 }
                    )
                    Tab(
                        text = { Text(stringResource(id = R.string.weight)) },
                        selected = state == 2,
                        onClick = { state = 2 }
                    )
                    
                }
                
                val setEntryModel: MutableList<FloatEntry> = mutableListOf()
                val repEntryModel: MutableList<FloatEntry> = mutableListOf()
                val weightEntryModel: MutableList<FloatEntry> = mutableListOf()

                data.forEachIndexed { index, exerciseData ->
                    setEntryModel.add(FloatEntry(index.toFloat(), exerciseData.sets.toFloat()))
                    repEntryModel.add(FloatEntry(index.toFloat(), exerciseData.reps.toFloat()))
                    weightEntryModel.add(FloatEntry(index.toFloat(), exerciseData.weight.toFloat()))
                }

                val chartEntryModel1 = entryModelOf(
                    setEntryModel,
                )
                val chartEntryModel2 = entryModelOf(
                    repEntryModel,
                )
                val chartEntryModel3 = entryModelOf(
                    weightEntryModel,
                )
                Chart(
                    chart = lineChart(
                        lines = listOf(
                            lineSpec(
                                lineColor = Color(0xFF6200EE),
                                dataLabel = textComponent{
                                    this.color = MaterialTheme.colorScheme.onBackground.toArgb()
                                }
                            ),
                        )
                    ),
                    model = if(state == 0) chartEntryModel1 else if (state == 1) chartEntryModel2 else  chartEntryModel3,
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(),
                )
            }
        }
    }
}