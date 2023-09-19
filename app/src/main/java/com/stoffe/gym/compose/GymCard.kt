package com.stoffe.gym.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.GymTheme
import com.stoffe.gym.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GymCard(
    modifier: Modifier = Modifier,
    cardTitle: String,
    subTitle: String? = null,
    onCardClick: () -> Unit,
    onCardLongClick: () -> Unit,
    onFirstIconClick: () -> Unit,
    onSecondIconClick: () -> Unit,
    @DrawableRes iconOne: Int?,
    @DrawableRes iconTwo: Int?,
    isActive: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .combinedClickable(
                onClick = onCardClick,
                onLongClick = onCardLongClick
            ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    Column() {
                        Text(text = cardTitle, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)

                        if (subTitle != null) {
                            Text(text = subTitle, fontSize = 16.sp)
                        }
                    }
                }
                if (iconOne != null) {
                    IconButton(onClick = onFirstIconClick) {
                        Icon(
                            painter = painterResource(id = iconOne),
                            contentDescription = "Play icon",
                            tint = if (!isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }
                if (iconTwo != null) {
                    IconButton(onClick = onSecondIconClick) {
                        Icon(
                            painter = painterResource(id = iconTwo),
                            contentDescription = "Play icon",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun GymCardPreview() {
    GymTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            GymCard(
                cardTitle = "Workout",
                onCardClick = { /*TODO*/ },
                onCardLongClick = {},
                onFirstIconClick = { /*TODO*/ },
                onSecondIconClick = { /*TODO*/ },
                iconOne = R.drawable.ic_play,
                iconTwo = R.drawable.ic_stop
            )
            GymCard(
                cardTitle = "Workout",
                subTitle = "2022-01-01",
                onCardClick = { /*TODO*/ },
                onCardLongClick = {},
                onFirstIconClick = { /*TODO*/ },
                onSecondIconClick = { /*TODO*/ },
                iconOne = R.drawable.ic_play,
                iconTwo = R.drawable.ic_stop
            )
        }
    }
}