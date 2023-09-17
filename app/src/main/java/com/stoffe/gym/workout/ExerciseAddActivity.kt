package com.stoffe.gym.workout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.database.entities.Exercise

class ExerciseAddActivity : AppCompatActivity() {
    private lateinit var workoutViewModel: WorkoutViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("workoutID", 0)
        setContent {
            workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
            ExerciseAddScreen(
                onCreateExercise = { exerciseName ->
                    workoutViewModel.insertExercise(Exercise(exerciseName, id))
                    finish()
                },
                onExit = { finish() }
            )
        }
    }
}