package com.example.medicineremainder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.medicineremainder.Model.Medicine
import com.example.medicineremainder.R
import com.example.medicineremainder.Utilities.SharedPrefHelper
import com.example.medicineremainder.Utilities.TimeHelper
import com.example.medicineremainder.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class StatisticsFragment : Fragment() {

    lateinit var binding: FragmentStatisticsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentStatisticsBinding.inflate(inflater,container,false)
        setupChart()
        return binding.root
    }



    fun setupChart() {
        val medicines: List<Medicine> = SharedPrefHelper(requireContext()).getUser()?.medicine ?: emptyList()

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        for ((index, med) in medicines.withIndex()) {
            val totalDays = TimeHelper.getAllDatesBetween(med.startDate, med.endDate).size
            val takenDays = med.takenDates.distinct().size

            val takenPercent = (takenDays.toFloat() / totalDays.toFloat()) * 100f
            val remainingPercent = 100f - takenPercent

            entries.add(BarEntry(index.toFloat(), floatArrayOf(takenPercent, remainingPercent)))
            labels.add(med.name)
        }

        val dataSet = BarDataSet(entries, "")
        val takenColor = ContextCompat.getColor(requireContext(), R.color.mainAppColor)
        val remainingColor = ContextCompat.getColor(requireContext(), R.color.customLightPurple)
        dataSet.setColors(takenColor, remainingColor)
       dataSet.stackLabels = arrayOf("Taken", "Missed")

        val barData = BarData(dataSet)
        barData.barWidth = 0.75f

        binding.barChart.data = barData
        binding.barChart.description.isEnabled = false
        binding.barChart.setFitBars(true)

        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        binding.barChart.axisLeft.axisMaximum = 100f
        binding.barChart.axisRight.isEnabled = false
        binding.barChart.invalidate()
    }

}