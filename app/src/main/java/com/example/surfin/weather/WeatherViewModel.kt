package com.example.surfin.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.CwaEarthquakeResult
import com.example.surfin.data.CwaTempResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.example.surfin.data.SurfinRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: SurfinRepository) : ViewModel() {

    private var _cwaTideResult = MutableLiveData<List<CwaTideResult>>()
    val cwaTideResult: LiveData<List<CwaTideResult>>
        get() = _cwaTideResult

    private var _cwaTempResult = MutableLiveData<List<CwaTempResult>>()
    val cwaTempResult: LiveData<List<CwaTempResult>>
        get() = _cwaTempResult

    private var _cwaWdsdResult = MutableLiveData<List<CwaTempResult>>()
    val cwaWdsdResult: LiveData<List<CwaTempResult>>
        get() = _cwaWdsdResult


    private var _cwaUviResult = MutableLiveData<List<CwaUviResult>>()
    val cwaUviResult: LiveData<List<CwaUviResult>>
        get() = _cwaUviResult

    private var _cwaEarthquakeResult = MutableLiveData<List<CwaEarthquakeResult>>()
    val cwaEarthquakeResult: LiveData<List<CwaEarthquakeResult>>
        get() = _cwaEarthquakeResult

    private fun getCwaTide() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTide()
                _cwaTideResult.value = listOf(dataList)
                Log.i("cwa", "tide success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "tide:fail ${e.message}")
            }
        }
    }


    private fun getCwaTemp() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTemp()
                _cwaTempResult.value = listOf(dataList)
                Log.i("cwa", "temp success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "temp:fail ${e.message}")
            }
        }
    }

    private fun getCwaWdsd() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWdsd("CAB505")
                _cwaWdsdResult.value = listOf(dataList)
                Log.i("cwa", "wdsd success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "wdsd:fail ${e.message}")
            }
        }
    }


    private fun getCwaUvi() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaUvi()
                _cwaUviResult.value = listOf(dataList)
                Log.i("cwa", "uvi success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "uvi:fail ${e.message}")
            }
        }
    }


    private fun getCwaEarthquake() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaEarthquake()
                _cwaEarthquakeResult.value = listOf(dataList)
                Log.i("cwa", "earthquake success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "earthquake:fail ${e.message}")
            }
        }
    }

    init {
        getCwaTemp()
        getCwaTide()
        getCwaWdsd()
        getCwaUvi()
        getCwaEarthquake()

    }
}