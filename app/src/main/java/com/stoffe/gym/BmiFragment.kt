package com.stoffe.gym

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.github.mikephil.charting.charts.LineChart

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.stoffe.gym.Helpers.Utils
import com.stoffe.gym.database.BmiViewModel
import com.stoffe.gym.database.entities.BMI
import com.stoffe.gym.databinding.FragmentBmiBinding
import kotlinx.coroutines.launch


class BmiFragment : Fragment() {
    private lateinit var linechart: LineChart
    private lateinit var binding: FragmentBmiBinding
    private lateinit var bmiViewModel: BmiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        bmiViewModel = ViewModelProvider(requireActivity()).get(
            BmiViewModel::class.java
        )
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bmi, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val weightEntries = ArrayList<Entry>()
        val repsEntries = ArrayList<Entry>()
        linechart = binding.lineChart
        linechart.axisLeft.setDrawGridLines(false)
        linechart.xAxis.setDrawGridLines(false)
        linechart.axisRight.setDrawGridLines(false)
        linechart.description.text = getString(R.string.bmi_graphh_label)

        viewLifecycleOwner.lifecycleScope.launch {
            bmiViewModel.allBMI.collect { bmi ->
                if (bmi == null) {
                    return@collect
                }
                if (bmi.isEmpty()) {
                    return@collect
                }
                bmi.forEachIndexed { index, data ->
                    if (data != null) {
                        weightEntries.add(Entry(index.toFloat(), data.weight))
                        repsEntries.add(Entry(index.toFloat(), Utils.calculateBMI(data.weight,data.height)))
                    }
                }
                val weightDataSet = LineDataSet(weightEntries, getString(R.string.weight))
                val bmiDataSet = LineDataSet(repsEntries, getString(R.string.bmi_title))
                weightDataSet.lineWidth = 4f
                weightDataSet.valueTextSize = 12f
                bmiDataSet.lineWidth = 4f
                bmiDataSet.valueTextSize = 12f
                bmiDataSet.setColors(Color.GREEN)
                linechart.notifyDataSetChanged()
                val dataSets = ArrayList<ILineDataSet>()
                dataSets.add(weightDataSet)
                dataSets.add(bmiDataSet)
                val data = LineData(dataSets)
                linechart.data = data
                linechart.invalidate()
            }
        }

        binding.toolbar.setNavigationOnClickListener { view12: View? ->
            NavHostFragment.findNavController(this@BmiFragment).popBackStack()
        }

        binding.toolbar.title = getString(R.string.bmi_title)
        super.onViewCreated(view, savedInstanceState)
    }
}



