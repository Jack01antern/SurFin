package com.example.surfin.weather

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentWeatherBinding
import com.example.surfin.factory.WeatherFactory

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args by navArgs<WeatherFragmentArgs>()
        Log.i("safe args", args.tempId.tempStationId)
        binding = FragmentWeatherBinding.inflate(inflater)

        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this,
            WeatherFactory(repository, args)
        ).get(WeatherViewModel::class.java)


        viewModel.cwaUviResult.observe(viewLifecycleOwner, Observer {
            binding.uviValue.text = it.records.weatherElement.location[0].value.toString()
        })

        viewModel.cwaTempResult.observe(viewLifecycleOwner, Observer {
            binding.tempValue.text = it.records.location[0].weatherElement[0].elementValue
        })

        viewModel.cwaWdsdResult.observe(viewLifecycleOwner, Observer {
            binding.wdsdValue.text = it.records.location[0].weatherElement[0].elementValue
        })

        viewModel.cwaWeatherResult.observe(viewLifecycleOwner, Observer {
            binding.weatherValue.text = it.records.location[0].weatherElement[0].elementValue
        })


        viewModel.cwaTideResult.observe(viewLifecycleOwner, Observer {
            binding.tideValue.text = it.toString()
        })
        return binding.root
    }

}