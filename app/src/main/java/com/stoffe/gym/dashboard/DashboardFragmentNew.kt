package com.stoffe.gym.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.stoffe.gym.R
import com.stoffe.gym.dashboard.bmi.BmiAddActivity
import com.stoffe.gym.dashboard.workout.WorkoutAddActivity
import com.stoffe.gym.database.BmiViewModel
import com.stoffe.gym.database.WorkoutViewModel


class DashboardFragmentNew : Fragment() {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var bmiViewModel: BmiViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        workoutViewModel = ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
        bmiViewModel = ViewModelProvider(requireActivity())[BmiViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                val item by workoutViewModel.allWorkouts.collectAsState(initial = listOf())
                val bmi by bmiViewModel.allBMI.collectAsState(initial = null)
                DashboardScreen(
                    workouts = item,
                    onWorkoutCardClick = { workout ->
                        workoutViewModel.setCurrentWorkout(workout)
                        val bundle = Bundle()
                        bundle.putInt("workoutID",workout.uid)
                        NavHostFragment.findNavController(this@DashboardFragmentNew)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
                    },
                    onWorkoutCardLongClick = { workout -> workoutViewModel.deleteWorkout(workout)},
                    navController = NavHostFragment.findNavController(this@DashboardFragmentNew),
                    onWorkoutStartClick = {workout -> workoutViewModel.updateWorkout(workout)},
                    onBmiClick = { NavHostFragment.findNavController(this@DashboardFragmentNew)
                        .navigate(R.id.action_FirstFragment_to_bmiFragment) },
                    bmi = bmi,
                    onCreateWorkout = {
                        val intent = Intent(activity, WorkoutAddActivity::class.java)
                        startActivity(intent)
                    },
                    onCreateBmi = {
                        val intent = Intent(activity, BmiAddActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setTranslationZ(view, 100f);
    }

}