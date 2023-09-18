package com.stoffe.gym.dashboard.workout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.database.entities.Workout

class WorkoutAddActivity : AppCompatActivity() {
    private lateinit var workoutViewModel: WorkoutViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
            WorkoutAddScreen(
                onCreateWorkout = { workoutName ->
                    workoutViewModel.insertWorkout(Workout(workoutName))
                    finish()
                },
                onExit = { finish() }
            )
        }
    }
}