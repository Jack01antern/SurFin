package com.example.surfin.weather

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentWeatherBinding
import com.example.surfin.factory.WeatherFactory
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args by navArgs<WeatherFragmentArgs>()
        Log.i("cwa safe args", args.tempId.tempStationId)
        binding = FragmentWeatherBinding.inflate(inflater)

        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this,
            WeatherFactory(repository, args)
        ).get(WeatherViewModel::class.java)


        viewModel.cwaUviResult.observe(viewLifecycleOwner, Observer {
            binding.uviValue.text = viewModel.cwaUviResult.value.toString()
        })

        viewModel.cwaTempResult.observe(viewLifecycleOwner, Observer {
            binding.tempValue.text = viewModel.cwaTempResult.value.toString()
        })

        viewModel.cwaWdsdResult.observe(viewLifecycleOwner, Observer {
            binding.wdsdValue.text = viewModel.cwaWdsdResult.value.toString()
        })

        viewModel.cwaWeatherResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                "晴" -> binding.weatherValue.setImageResource(R.drawable.sun)
                "多雲" -> binding.weatherValue.setImageResource(R.drawable.cloudy)
                "陰" -> binding.weatherValue.setImageResource(R.drawable.cloud)
                "多雲有雨" -> binding.weatherValue.setImageResource(R.drawable.rainy_cloudy)
                "陰有雨" -> binding.weatherValue.setImageResource(R.drawable.rain)
                else -> binding.weatherValue.setImageResource(R.drawable.cloud)
            }
        })


        viewModel.cwaTideResult.observe(viewLifecycleOwner, Observer {
            viewModel.cwaTideResult.value?.let { setLineChartData(it) }

        })

        return binding.root
    }

    private fun setLineChartData(entries: List<Entry>) {

        Log.i("line chart", "$entries")

        val lineDataSet = LineDataSet(entries, "")
        val dataSets = listOf(lineDataSet)
        binding.lineChart.data = LineData(dataSets)

        lineDataSet.circleColors =
            listOf(ContextCompat.getColor(requireContext(), R.color.primary_navy))
        binding.lineChart.xAxis.isEnabled = false
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.primary_navy)
        binding.lineChart.animateXY(3000, 3000)

    }
}