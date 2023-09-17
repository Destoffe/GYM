package com.stoffe.gym.exercise

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.stoffe.gym.database.WorkoutViewModel

class ExerciseDataGraphActivity : AppCompatActivity() {
    private lateinit var workoutViewModel: WorkoutViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("exerciseID",0)
        setContent {
            workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
            val item by workoutViewModel.getExercisesDataById(id).collectAsState(initial = listOf())
            ExerciseDataGraphScreen(
                data = item,
                onExit = { finish() }
            )
        }
    }
}