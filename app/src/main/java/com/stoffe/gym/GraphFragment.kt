package com.stoffe.gym

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.github.mikephil.charting.charts.LineChart
import com.stoffe.gym.database.WorkoutViewModel
import com.stoffe.gym.database.entities.ExerciseData
import com.stoffe.gym.databinding.FragmentGraphBinding

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class GraphFragment : Fragment() {
    private lateinit var linechart: LineChart
    private lateinit var binding: FragmentGraphBinding
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        workoutViewModel = ViewModelProvider(requireActivity()).get(
            WorkoutViewModel::class.java
        )
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_graph, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val weightEntries = ArrayList<Entry>()
        val repsEntries = ArrayList<Entry>()
        val setsEntries = ArrayList<Entry>()
        linechart = binding.lineChart
        linechart.axisLeft.setDrawGridLines(false)
        linechart.xAxis.setDrawGridLines(false)
        linechart.axisRight.setDrawGridLines(false)
        linechart.description.text = "Exercise Data"

        workoutViewModel.currentExercise.value?.let {
            workoutViewModel.getAllExercisesDataWithId(it.uid).observe(
                viewLifecycleOwner,
                { exerciseData: List<ExerciseData?>? ->
                    if (exerciseData == null) {
                        return@observe
                    }

                    exerciseData.forEachIndexed { index, data ->
                        if (data != null) {
                            weightEntries.add(Entry(index.toFloat(), data.weight.toFloat()))
                            repsEntries.add(Entry(index.toFloat(), data.reps.toFloat()))
                            setsEntries.add(Entry(index.toFloat(), data.sets.toFloat()))
                        }
                    }
                    val weightDataSet = LineDataSet(weightEntries, "Weight")
                    val repsDataSet = LineDataSet(repsEntries, "Reps")
                    val setsDataSet = LineDataSet(setsEntries, "Sets")
                    weightDataSet.lineWidth = 4f
                    weightDataSet.valueTextSize = 12f
                    repsDataSet.lineWidth = 4f
                    repsDataSet.valueTextSize = 12f
                    repsDataSet.setColors(Color.GREEN)
                    setsDataSet.lineWidth = 4f
                    setsDataSet.valueTextSize = 12f
                    setsDataSet.setColors(Color.RED)
                    linechart.notifyDataSetChanged()
                    val dataSets = ArrayList<ILineDataSet>()
                    dataSets.add(weightDataSet)
                    dataSets.add(repsDataSet)
                    dataSets.add(setsDataSet)
                    val data = LineData(dataSets)
                    linechart.data = data
                    linechart.invalidate()

                })

            binding.toolbar.title = it.name
        }

        binding.toolbar.setNavigationOnClickListener { view12: View? ->
            NavHostFragment.findNavController(this@GraphFragment)
                .navigate(R.id.action_graphFragment_to_SecondFragment)
        }


        super.onViewCreated(view, savedInstanceState)
    }
}



