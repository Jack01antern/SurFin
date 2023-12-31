package com.example.surfin.weather

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentWeatherBinding
import com.example.surfin.factory.WeatherFactory
import com.example.surfin.util.WeatherValue
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IFillFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val args by navArgs<WeatherFragmentArgs>()
        Log.i("cwa safe args", args.tempId.tempStationId)
        binding = FragmentWeatherBinding.inflate(inflater)

        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this, WeatherFactory(repository, args)
        ).get(WeatherViewModel::class.java)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getCwaUvi()
            viewModel.getCwaWdsd()
            viewModel.getCwaTide()
            viewModel.getCwaTemp()
            viewModel.getCwaWave()
            viewModel.getCwaWeather()
            binding.swipeRefreshLayout.isRefreshing = false
        }


        viewModel.cwaUviResult.observe(viewLifecycleOwner) {
            if (it == "-99") {
                binding.uviValue.text = 4.05.toString()
                binding.uviValue.setTextColor(resources.getColor(R.color.uvi_yellow))
            } else {
                binding.uviValue.text = viewModel.cwaUviResult.value.toString()
            }

            val uvi = it!!.toFloat()
            when {
                uvi in 0f..2.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_green))
                    binding.uviDescription.text = getString(R.string.uvi_low)
                }

                uvi in 3f..5.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_yellow))
                    binding.uviDescription.text = getString(R.string.uvi_mod)
                }

                uvi in 6f..7.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_orange))
                    binding.uviDescription.text = getString(R.string.uvi_high)
                }

                uvi in 8f..10.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_red))
                    binding.uviDescription.text = getString(R.string.uvi_over)
                }

                uvi >= 11f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_purple))
                    binding.uviDescription.text = getString(R.string.uvi_extreme)
                }

            }
        }

        viewModel.cwaTempResult.observe(viewLifecycleOwner) {
            if (it == "-99.0") {
                binding.tempValue.text = 27.5.toString()
            } else {
                binding.tempValue.text = it.toString()
            }
        }

        viewModel.cwaWdsdResult.observe(viewLifecycleOwner) {
            if (it == "0.0" || it == "-99.0") {
                binding.wdsdValue.text = 0.8.toString()
            } else {
                binding.wdsdValue.text = it.toString()
            }
        }

        viewModel.cwaWaveResult.observe(viewLifecycleOwner) {
            if (it == "NONE") {
                binding.waveValue.text = 1.5.toString()
            } else {
                binding.waveValue.text = it.toString()
            }
        }

        viewModel.cwaWeatherResult.observe(viewLifecycleOwner) { description ->
            val matchedWeatherValue =
                WeatherValue.values().find { getString(it.descriptionResId) == description }

            val animationResId = when (matchedWeatherValue) {
                WeatherValue.SUNNY -> R.raw.animation_sunny
                WeatherValue.SUNNY_CLOUD -> R.raw.animation_sunny_cloud
                WeatherValue.CLOUDY -> R.raw.animation_cloudy
                WeatherValue.CLOUDY_RAIN -> R.raw.animation_cloudy_rain
                WeatherValue.RAINY -> R.raw.animation_rainy
                else -> R.raw.animation_sunny_cloud
            }

            binding.weatherValue.setAnimation(animationResId)
        }


        viewModel.cwaTideResult.observe(viewLifecycleOwner) {
            viewModel.cwaTideResult.value?.let { setLineChartData(it) }
        }

        binding.backKey.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.locationTitle.text = args.tempId.title

        return binding.root
    }


    private fun setLineChartData(entries: List<Entry>) {

        Log.i("line chart", "$entries")

        //feed data
        val lineDataSet = LineDataSet(entries, "")
        val dataSets = listOf(lineDataSet)
        binding.lineChart.data = LineData(dataSets)


        //set line chart style
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.line_chart_gradient)
        lineDataSet.lineWidth = 3f
        lineDataSet.circleColors =
            listOf(ContextCompat.getColor(requireContext(), R.color.primary_gray))
        lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.line_chart_blue)
        lineDataSet.valueTextSize = 14f
        lineDataSet.setValueTextColors(listOf(resources.getColor(R.color.primary_navy)))
        lineDataSet.fillFormatter = IFillFormatter { _, dataProvider ->
            dataProvider.yChartMin
        }


        binding.lineChart.legend.isEnabled = false
        binding.lineChart.axisLeft.isEnabled = false
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.description.isEnabled = false
        binding.lineChart.animateY(2000, Easing.EasingOption.EaseInOutCubic)
        binding.lineChart.setPinchZoom(true)

        val xAxis = binding.lineChart.xAxis
        xAxis.valueFormatter = object : IAxisValueFormatter {
            @SuppressLint("ConstantLocale")
            private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val millis = value.toLong()
                val date = Date(millis)
                return dateFormat.format(date)
            }
        }

        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }
}