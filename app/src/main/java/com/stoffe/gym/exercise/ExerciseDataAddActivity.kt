package com.stoffe.gym.exercise

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.database.entities.ExerciseData

class ExerciseDataAddActivity : AppCompatActivity() {
    private lateinit var workoutViewModel: WorkoutViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("exerciseID",0)
        setContent {
            workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
            ExerciseDataAddScreen(
                onCreateExercise = { sets,reps,weight ->
                    workoutViewModel.insertExerciseData(ExerciseData(sets = sets, reps = reps, weight = weight, exerciseID = id))
                    finish()
                },
                onExit = { finish() }
            )
        }
    }
}