package com.stoffe.gym.workout

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
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.exercise.ExerciseDataGraphActivity


class WorkoutFragmentNew : Fragment() {
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
                val workoutID = arguments?.getInt("workoutID")

                val item by workoutViewModel.getExercisesById(workoutID!!).collectAsState(initial = listOf())
                val currentWorkout = workoutViewModel.getCurrentWorkout().value
                WorkoutScreen(
                    exercises = item,
                    currentWorkout = currentWorkout!!,
                    onExerciseCardClick = { exercise ->
                        val bundle = Bundle()
                        bundle.putInt("exerciseID",exercise.uid)
                        workoutViewModel.setCurrentExercise(exercise)
                        NavHostFragment.findNavController(this@WorkoutFragmentNew)
                            .navigate(R.id.action_SecondFragment_to_exerciseFragment,bundle)
                    },
                    onExerciseCardLongClick = { exercise ->
                        workoutViewModel.deleteExercise(exercise)
                    },
                    navController = NavHostFragment.findNavController(this@WorkoutFragmentNew),
                    onBackArrowClick = { NavHostFragment.findNavController(this@WorkoutFragmentNew).popBackStack()},
                    onCreateExercise = {
                        val intent = Intent(activity, ExerciseAddActivity::class.java)
                        intent.putExtra("workoutID",workoutID)
                        startActivity(intent)
                    },
                    onGraphIconClicked = {exerciseID ->
                        val intent = Intent(activity, ExerciseDataGraphActivity::class.java)
                        intent.putExtra("exerciseID",exerciseID)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}