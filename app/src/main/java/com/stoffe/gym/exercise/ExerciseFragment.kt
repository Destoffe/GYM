package com.stoffe.gym.exercise

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
import com.stoffe.gym.Adapters.ExerciseDataAdapter
import com.stoffe.gym.Adapters.LogDataDialogLayout
import com.stoffe.gym.Helpers.Utils
import com.stoffe.gym.R
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.database.entities.ExerciseData
import com.stoffe.gym.databinding.FragmentExerciseBinding
import kotlinx.coroutines.launch

class ExerciseFragment : Fragment() {
    private var binding: FragmentExerciseBinding? = null
    private var viewModel: WorkoutViewModel? = null
    var exerciseDataAdapter: ExerciseDataAdapter? = null
    var exerciseDataList: MutableList<ExerciseData>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(WorkoutViewModel::class.java)
        exerciseDataList = ArrayList()
        exerciseDataAdapter = ExerciseDataAdapter(null, { exercise: ExerciseData ->
            val builder = AlertDialog.Builder(requireContext(), R.style.dialog_style)
            builder.setTitle(getString(R.string.delete_exercise_log))
            builder.setPositiveButton(R.string.positive_button) { dialog: DialogInterface?, which: Int ->
                viewModel!!.deleteExerciseData(exercise)
                (exerciseDataList as ArrayList<ExerciseData>).remove(exercise)
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            builder.show()
        }, null)
        exerciseDataAdapter!!.setData(exerciseDataList)
        binding!!.recycleView.adapter = exerciseDataAdapter
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { view12: View? ->
            NavHostFragment.findNavController(this@ExerciseFragment)
                .navigate(R.id.action_exerciseFragment_to_SecondFragment)
        }
        viewModel!!.getCurrentExercise().observe(viewLifecycleOwner) { exercise ->
            if (exercise == null) {
                return@observe
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel!!.getExercisesDataById(exercise.uid).collect { exerciseData ->
                    exerciseDataList = exerciseData as MutableList<ExerciseData>?
                    exerciseDataAdapter!!.setData(exerciseDataList)
                }
            }
            binding!!.toolbar.title = exercise.name
        }
        binding!!.fabExercise.setOnClickListener { view1: View? ->
            val builder = AlertDialog.Builder(requireContext(), R.style.dialog_style)
            builder.setTitle(R.string.create_new_exercise_data)
            val LL = LogDataDialogLayout(context)
            builder.setView(LL)
            builder.setPositiveButton(R.string.positive_button) { dialog: DialogInterface?, which: Int ->
                if (TextUtils.isEmpty(LL.setsEditText.text) || TextUtils.isEmpty(LL.repsEditText.text) || TextUtils.isEmpty(
                        LL.weightEditText.text
                    )
                ) {
                    Utils.showSnackbar(getString(R.string.exercise_data_missing), binding!!.root)
                    return@setPositiveButton
                }
                val sets = LL.setsEditText.text.toString().toInt()
                val reps = LL.repsEditText.text.toString().toInt()
                val weight = LL.weightEditText.text.toString().toInt()
                val exerciseData =
                    ExerciseData(sets, reps, weight, viewModel!!.getCurrentExercise().value!!.uid)
                viewModel!!.insertExerciseData(exerciseData)
                Utils.showSnackbar(getString(R.string.exercise_data_added), binding!!.root)
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            builder.show()
        }

    }
}