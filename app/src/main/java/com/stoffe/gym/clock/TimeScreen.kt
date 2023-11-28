package com.stoffe.gym.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@Composable
fun TimeScreen() {
    var timerState by remember { mutableStateOf(TimerState.Stopped) }
    var timerValue by remember { mutableStateOf(0) }

    DisposableEffect(timerState) {
        var job: Job? = null

        if (timerState == TimerState.Running) {
            job = startTimer(timerValue) {
                timerValue = it
            }
        }

        onDispose {
            job?.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = formatTime(timerValue), style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimerButton(
                text = if (timerState == TimerState.Running) "Pause" else "Start",
                icon = if (timerState == TimerState.Running) Icons.Default.Pause else Icons.Default.PlayArrow
            ) {
                timerState = when (timerState) {
                    TimerState.Stopped -> {
                        TimerState.Running
                    }

                    TimerState.Running -> {
                        TimerState.Paused
                    }

                    TimerState.Paused -> {
                        TimerState.Running
                    }
                }
            }

            TimerButton(
                text = "Restart",
                icon = Icons.Default.RestartAlt
            ) {
                timerState = TimerState.Stopped
                timerValue = 0
            }
        }
    }
}

@Composable
fun TimerButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = MaterialTheme.colorScheme.onPrimary)
    }
}

fun startTimer(initialValue: Int, onTick: (Int) -> Unit): Job {
    return CoroutineScope(Dispatchers.Default).launch {
        var currentTime = initialValue
        while (true) {
            delay(1000)
            currentTime++
            onTick(currentTime)
        }
    }
}

@Composable
fun formatTime(seconds: Int): String {
    val minutes = TimeUnit.SECONDS.toMinutes(seconds.toLong())
    val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

enum class TimerState {
    Running,
    Paused,
    Stopped
}