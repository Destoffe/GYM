package com.stoffe.gym.dashboard.bmi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.GymTheme
import com.stoffe.gym.Helpers.Utils
import com.stoffe.gym.R
import com.stoffe.gym.database.entities.BMI
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiCard(
    bmi: BMI,
    modifier: Modifier = Modifier,
    onBmiClick: () -> Unit,
) {
    Card(
        onClick = onBmiClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(text = stringResource(id = R.string.weight_bmi,bmi.weight))
                Text(text = stringResource(id = R.string.height_bmi,bmi.height))
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(text = stringResource(id = R.string.bmi_bmi, Utils.calculateBMI(bmi.weight,bmi.height)))
                Text(text = stringResource(id = R.string.height_bmi,bmi.height))
            }
        }

    }
}

@Preview
@Composable
fun BmiCardPreview() {
    GymTheme {
        BmiCard(
            bmi = BMI(1, 75f, 175f, 1, LocalDateTime.now(), 1),
            onBmiClick = {}
        )
    }
}