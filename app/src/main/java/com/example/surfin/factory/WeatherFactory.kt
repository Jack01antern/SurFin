package com.example.surfin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfin.data.SurfinRepository
import com.example.surfin.weather.WeatherFragmentArgs
import com.example.surfin.weather.WeatherViewModel

class WeatherFactory (
    private val repository: SurfinRepository,private val args: WeatherFragmentArgs
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(args,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

