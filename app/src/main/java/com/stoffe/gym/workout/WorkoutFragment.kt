package com.stoffe.gym.workout

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.stoffe.gym.Adapters.AddExerciseLayout
import com.stoffe.gym.Adapters.ExerciseAdapter
import com.stoffe.gym.Adapters.LogDataDialogLayout
import com.stoffe.gym.Helpers.Utils
import com.stoffe.gym.R
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.database.entities.Exercise
import com.stoffe.gym.database.entities.ExerciseData
import com.stoffe.gym.database.entities.Workout
import kotlinx.coroutines.launch

class WorkoutFragment() : Fragment() {
     lateinit var exerciseRecycleView: RecyclerView
    var exerciseAdapter: ExerciseAdapter? = null
    var exerciseList: MutableList<Exercise>? = null
    var viewModel: WorkoutViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseList = ArrayList()
        viewModel = ViewModelProvider(requireActivity()).get(WorkoutViewModel::class.java)
        exerciseAdapter = ExerciseAdapter({ exercise: Exercise? ->
            viewModel!!.setCurrentExercise(exercise!!)
            NavHostFragment.findNavController(this@WorkoutFragment)
                .navigate(R.id.action_SecondFragment_to_exerciseFragment)
        }, { exercise: Exercise ->
            val builder = AlertDialog.Builder(requireContext(), R.style.dialog_style)
            builder.setTitle(getString(R.string.delete_exercise) + " " + exercise.name + "?")
            builder.setPositiveButton(R.string.positive_button) { dialog: DialogInterface?, which: Int ->
                viewModel!!.deleteExercise(exercise)
                (exerciseList as ArrayList<Exercise>).remove(exercise)
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            builder.show()
        }, { exercise: Exercise ->
            val builder = AlertDialog.Builder(requireContext(), R.style.dialog_style)
            builder.setTitle(R.string.create_new_exercise_data)
            val LL = LogDataDialogLayout(context)
            builder.setView(LL)
            builder.setPositiveButton(R.string.positive_button) { dialog: DialogInterface?, which: Int ->
                if (TextUtils.isEmpty(LL.setsEditText.text) || TextUtils.isEmpty(LL.repsEditText.text) || TextUtils.isEmpty(
                        LL.weightEditText.text
                    )
                ) {
                    Utils.showSnackbar(getString(R.string.exercise_data_missing), getView())
                    return@setPositiveButton
                }
                val sets = LL.setsEditText.text.toString().toInt()
                val reps = LL.repsEditText.text.toString().toInt()
                val weight = LL.weightEditText.text.toString().toInt()
                val exerciseData = ExerciseData(sets, reps, weight, exercise.uid)
                viewModel!!.insertExerciseData(exerciseData)
                Utils.showSnackbar(getString(R.string.exercise_data_added), view)
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            builder.show()
        }) { exercise: Exercise? ->
            viewModel!!.setCurrentExercise(exercise!!)
            NavHostFragment.findNavController(this@WorkoutFragment)
                .navigate(R.id.action_SecondFragment_to_graphFragment)
        }
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { view12: View? ->
            NavHostFragment.findNavController(this@WorkoutFragment)
                .navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        viewModel!!.getCurrentWorkout().observe(viewLifecycleOwner) { workout: Workout? ->
            if (workout == null) {
                return@observe
            }
            toolbar.title = workout.getName()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel!!.getExercisesById(workout.uid).collect { exercises ->
                    exerciseList = exercises.toMutableList()
                    exerciseAdapter!!.setData(exerciseList)
                }
            }
        }

        exerciseRecycleView = view.findViewById(R.id.recycle_view)
        exerciseRecycleView.adapter = exerciseAdapter
        val button = view.findViewById<ExtendedFloatingActionButton>(R.id.fab_exercise)
        button.setOnClickListener { view1: View? ->
            val builder = AlertDialog.Builder(requireContext(), R.style.dialog_style)
            builder.setTitle(R.string.create_new_exercise)
            val LL = AddExerciseLayout(context, false, true)
            LL.setBackgroundColor(resources.getColor(R.color.white_background))
            LL.setEditText(getString(R.string.hint_exercise_name))
            builder.setView(LL)
            builder.setPositiveButton(R.string.positive_button) { dialog: DialogInterface?, which: Int ->
                if (!TextUtils.isEmpty(LL.editText.text)) {
                    val exercise = Exercise(
                        LL.editText.text.toString(),
                        viewModel!!.getCurrentWorkout().value!!.uid
                    )
                    viewModel!!.insertExercise(exercise)
                } else {
                    Utils.showSnackbar(getString(R.string.dialog_workout_created_error), view)
                }
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            builder.show()
        }
    }
}