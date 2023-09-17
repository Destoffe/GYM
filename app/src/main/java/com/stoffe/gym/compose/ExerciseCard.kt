package com.stoffe.gym.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.GymTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseCard(
    setsText: String,
    repsText: String,
    weightText: String,
    dateText: String,
    onCardClick: () -> Unit,
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(text = setsText)
                Text(text = repsText)
                Text(text = weightText)
            }
            Text(text = dateText)
        }
    }
}


@Preview
@Composable
fun ExerciseCardPreview() {
    GymTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ExerciseCard(setsText = "2", repsText = "2", weightText = "2", dateText = "2022-01-01") {
            }
        }
    }
}