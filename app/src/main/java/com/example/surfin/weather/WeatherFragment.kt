package com.example.surfin.weather

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentWeatherBinding
import com.example.surfin.factory.WeatherFactory
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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
            binding.swipeRefreshLayout.isRefreshing = false
        }


        viewModel.cwaUviResult.observe(viewLifecycleOwner, Observer {
            binding.uviValue.text = viewModel.cwaUviResult.value.toString()
            val uvi = viewModel.cwaUviResult.value!!.toFloat()
            when {
                uvi in 0f..2.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_green))
                }

                uvi in 3f..5.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_yellow))
                }

                uvi in 6f..7.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_orange))
                }

                uvi in 8f..10.99f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_red))
                }

                uvi >= 11f -> {
                    binding.uviValue.setTextColor(resources.getColor(R.color.uvi_purple))
                }
            }
        })

        viewModel.cwaTempResult.observe(viewLifecycleOwner, Observer {
            binding.tempValue.text = viewModel.cwaTempResult.value.toString()
        })

        viewModel.cwaWdsdResult.observe(viewLifecycleOwner, Observer {
            binding.wdsdValue.text = viewModel.cwaWdsdResult.value.toString()
        })

        viewModel.cwaWeatherResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                "晴" -> binding.weatherValue.setAnimation(R.raw.animation_sunny)
                "多雲" -> binding.weatherValue.setAnimation(R.raw.animation_sunny_cloud)
                "陰" -> binding.weatherValue.setAnimation(R.raw.animation_cloudy)
                "多雲有雨" -> binding.weatherValue.setAnimation(R.raw.animation_cloudy_rain)
                "陰有雨" -> binding.weatherValue.setAnimation(R.raw.animation_rainy)
                else -> binding.weatherValue.setAnimation(R.raw.animation_sunny_cloud)
            }
        })


        viewModel.cwaTideResult.observe(viewLifecycleOwner, Observer {
            viewModel.cwaTideResult.value?.let { setLineChartData(it) }

        })

        binding.backKey.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.locationTitle.text = args.tempId.title
        binding.lineChart.setPinchZoom(true)

        return binding.root
    }


    private fun setLineChartData(entries: List<Entry>) {

        Log.i("line chart", "$entries")

        //feed data
        val lineDataSet = LineDataSet(entries, "")
        val dataSets = listOf(lineDataSet)
        binding.lineChart.data = LineData(dataSets)


        //set line chart style
        val xAxis = binding.lineChart.xAxis
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.line_chart_gradient)
        lineDataSet.lineWidth = 3f
        lineDataSet.circleColors =
            listOf(ContextCompat.getColor(requireContext(), R.color.primary_gray))
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.line_chart_blue)
        lineDataSet.valueTextSize = 14f
        lineDataSet.setValueTextColors(listOf(resources.getColor(R.color.primary_navy)))


        binding.lineChart.legend.isEnabled = false
        binding.lineChart.axisLeft.isEnabled = false
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.description.isEnabled = false

        xAxis.valueFormatter = object : IAxisValueFormatter {
            private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val millis = value.toLong()
                val date = Date(millis)
                return dateFormat.format(date)
            }
        }

        xAxis.setDrawGridLines(false)
        xAxis.granularity = 2f
        xAxis.isGranularityEnabled = true
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        binding.lineChart.animateXY(2000, 3000)
        lineDataSet.fillFormatter = IFillFormatter { _, dataProvider ->
            entries
            dataProvider.yChartMin
        }
    }


}