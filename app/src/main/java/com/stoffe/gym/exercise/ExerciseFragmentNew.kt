package com.stoffe.gym.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.stoffe.gym.R
import com.stoffe.gym.dashboard.WorkoutAddActivity
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.workout.WorkoutScreen

class ExerciseFragmentNew : Fragment() {
    private lateinit var workoutViewModel: WorkoutViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        workoutViewModel = ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val exerciseID = arguments?.getInt("exerciseID")
                val item by workoutViewModel.getExercisesDataById(exerciseID!!).collectAsState(initial = listOf())
                val currentExercise = workoutViewModel.getCurrentExercise().value
                ExerciseScreen(
                    exerciseData = item,
                    currentExercise = currentExercise!!,
                    onExerciseCardClick = {},
                    onBackArrowClick = { /*TODO*/ },
                    navController = NavHostFragment.findNavController(this@ExerciseFragmentNew),
                    onFabClick = {
                        val intent = Intent(activity, ExerciseDataAddActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}