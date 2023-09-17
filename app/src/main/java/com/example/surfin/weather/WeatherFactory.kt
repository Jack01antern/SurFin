package com.example.surfin.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfin.data.SurfinRepository
import com.example.surfin.util.SurfinDataSource

class WeatherFactory (
    private val repository: SurfinRepository,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

