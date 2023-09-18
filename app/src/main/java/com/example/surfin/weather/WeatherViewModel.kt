package com.example.surfin.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.CwaTempResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.example.surfin.data.SurfinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
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

    private fun getCwaTide() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTide()
                _cwaTideResult.value = listOf(dataList)
                Log.i("Tide", "Tide success: $dataList")
            } catch (e: Exception) {
                Log.i("Tide", "Tide:fail ${e.message}")
            }
        }
    }


    private fun getCwaTemp() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTemp()
                _cwaTempResult.value = listOf(dataList)
                Log.i("temp", "temp success: $dataList")
            } catch (e: Exception) {
                Log.i("temp", "temp:fail ${e.message}")
            }
        }
    }

    private fun getCwaWdsd() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWdsd("CAB050")
                _cwaWdsdResult.value = listOf(dataList)
                Log.i("wdsd", "wdsd success: $dataList")
            } catch (e: Exception) {
                Log.i("wdsd", "wdsd:fail ${e.message}")
            }
        }
    }


    private fun getCwaUvi(): Flow<CwaUviResult> = flow{
        viewModelScope.launch {
            try {
                val response = repository.getCwaUvi()
                emitAll(response)

//                _cwaUviResult.value = listOf(response)
                Log.i("uvi", "uvi success: $response")
            } catch (e: Exception) {
                Log.i("uvi", "uvi:fail ${e.message}")
            }
        }
    }

    init {
        getCwaTemp()
        getCwaTide()
        getCwaWdsd()
        getCwaUvi()
    }
}