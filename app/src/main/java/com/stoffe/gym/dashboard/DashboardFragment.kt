package com.stoffe.gym.dashboard

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.stoffe.gym.Adapters.AddBmiLayout
import com.stoffe.gym.Adapters.AddExerciseLayout
import com.stoffe.gym.Adapters.WorkoutAdapter
import com.stoffe.gym.Adapters.WorkoutAdapter.OnButtonClickListener
import com.stoffe.gym.Helpers.Utils
import com.stoffe.gym.R
import com.stoffe.gym.database.DashboardViewModel
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.database.entities.BMI
import com.stoffe.gym.database.entities.Summary
import com.stoffe.gym.database.entities.Workout
import com.stoffe.gym.databinding.FragmentDashboardBinding
import java.time.LocalDateTime

class DashboardFragment : Fragment() {
    private var workoutAdapter: WorkoutAdapter? = null
    private lateinit var recyclerView: RecyclerView
    var testData: MutableList<Workout>? = null
    private var viewModel: WorkoutViewModel? = null
    private lateinit var dashboardViewModel: DashboardViewModel
    private var binding: FragmentDashboardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutAdapter = WorkoutAdapter({ workout: Workout? ->
            viewModel!!.setCurrentWorkout(workout)
            NavHostFragment.findNavController(this@DashboardFragment)
                .navigate(R.id.action_FirstFragment_to_SecondFragment)
        }, { workout: Workout ->
            val builder = AlertDialog.Builder(
                requireContext(), R.style.dialog_style
            )
            builder.setTitle(R.string.delete_workout)
            builder.setPositiveButton(R.string.positive_button) { dialog: DialogInterface?, which: Int ->
                viewModel!!.deleteWorkout(workout)
                testData!!.remove(workout)
                Utils.showSnackbar("Workout deleted", binding!!.root)
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            builder.show()
        }, OnButtonClickListener { workout: Workout ->
            var i = 0
            while (i < workoutAdapter!!.dataSet.size) {
                if (workoutAdapter!!.dataSet[i].isActive && workoutAdapter!!.dataSet[i].getUid() != workout.getUid()) {
                    Utils.showSnackbar("Another workout is already active!", binding!!.root)
                }
                i++
            }
            workout.setActive(!workout.isActive)
            viewModel!!.updateWorkout(workout)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //testData = generateDumbData();
        viewModel = ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
        dashboardViewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
        val fab_workout = view.findViewById<ExtendedFloatingActionButton>(R.id.fab_workout)
        val fab_bmi = view.findViewById<ExtendedFloatingActionButton>(R.id.fab_bmi)
        val fab = view.findViewById<ExtendedFloatingActionButton>(R.id.fab)
        fab_bmi.hide()
        fab_workout.hide()
        recyclerView = view.findViewById(R.id.list_view)
        fab_workout.setOnClickListener { view1: View? ->
            val builder = AlertDialog.Builder(requireContext(), R.style.dialog_style)
            builder.setTitle(R.string.create_new_workout)
            val LL = AddExerciseLayout(context, false, true)
            LL.setBackgroundColor(resources.getColor(R.color.white_background))
            LL.setEditText(getString(R.string.hint_workout_name))
            builder.setView(LL)
            builder.setPositiveButton(R.string.positive_button) { _: DialogInterface?, _: Int ->
                if (!TextUtils.isEmpty(LL.editText.text)) {
                    val workout = Workout(LL.editText.text.toString())
                    testData!!.add(workout)
                    viewModel!!.insertWorkout(workout)
                    workoutAdapter!!.setData(testData)
                    Utils.showSnackbar(getString(R.string.dialog_workout_created), binding!!.root)
                } else {
                    Utils.showSnackbar(
                        getString(R.string.dialog_workout_created_error),
                        binding!!.root
                    )
                }
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            builder.show()
        }
        fab.setOnClickListener {
            if (!fab_bmi.isShown && !fab_workout.isShown) {
                fab_bmi.show()
                fab_workout.show()
            } else {
                fab_bmi.hide()
                fab_workout.hide()
            }
        }
        fab_bmi.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext(), R.style.dialog_style)
            builder.setTitle(R.string.add_body_data)
            val LL = AddBmiLayout(context)
            builder.setView(LL)
            builder.setPositiveButton(R.string.positive_button) { _: DialogInterface?, _: Int ->
                if (TextUtils.isEmpty(LL.ageEditText.text) || TextUtils.isEmpty(LL.heightEditText.text) || TextUtils.isEmpty(
                        LL.weightEditText.text
                    )
                ) {
                    Utils.showSnackbar(getString(R.string.bmi_data_missing), binding!!.root)
                    return@setPositiveButton
                }
                val age = LL.ageEditText.text.toString().toInt()
                val height = LL.heightEditText.text.toString().toFloat()
                val weight = LL.weightEditText.text.toString().toFloat()
                var gender = 0
                gender = if (LL.maleRadio.isChecked) {
                    0
                } else {
                    1
                }
                val bmi = BMI(
                    weight = weight,
                    height = height,
                    gender = gender,
                    date = LocalDateTime.now(),
                    age = age
                )
                dashboardViewModel.insertBmi(bmi)
                Utils.showSnackbar(getString(R.string.bmi_data_added), binding!!.root)
            }
            builder.setNegativeButton(R.string.negative_button) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            builder.show()
        }
        testData = ArrayList()
        workoutAdapter!!.setData(testData)
        recyclerView.adapter = workoutAdapter
        viewModel!!.allWorkouts.observe(viewLifecycleOwner) { workouts: MutableList<Workout>? ->
            if (workouts == null) {
                binding!!.isWorkoutListEmpty = true
                return@observe
            }
            binding!!.isWorkoutListEmpty = workouts.size < 1
            testData = workouts
            workoutAdapter!!.setData(testData)
        }
        viewModel!!.allSummaries.observe(viewLifecycleOwner) { summaries: List<Summary?>? ->
            if (summaries == null || summaries.isEmpty()) {
                return@observe
            }
            binding!!.summary = summaries[0]
        }
        dashboardViewModel.allBMI.observe(viewLifecycleOwner) { bmi: List<BMI?>? ->
            if (bmi == null) {
                return@observe
            }
            if (bmi.isEmpty()) {
                return@observe
            }
            val latestBmi = bmi[bmi.size - 1]
            binding!!.bmiCard.setBmi(latestBmi)
            binding!!.bmi = latestBmi
        }

        binding?.bmiCard?.bmiCard?.setOnClickListener {
            NavHostFragment.findNavController(this@DashboardFragment)
                .navigate(R.id.action_FirstFragment_to_bmiFragment)
        }
    }

    fun generateDumbData(): List<Workout> {
        val dumbData: MutableList<Workout> = ArrayList()
        val workout1 = Workout()
        workout1.setName("workout1")
        val workout2 = Workout()
        workout2.setName("workout2")
        val workout3 = Workout()
        workout3.setName("workout3")
        dumbData.add(workout1)
        dumbData.add(workout2)
        dumbData.add(workout3)
        return dumbData
    }
}