package com.example.surfin.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.CwaTempResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.example.surfin.network.SurfinApi
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private var _cwaTideResult = MutableLiveData<List<CwaTideResult>>()
    val cwaTideResult: LiveData<List<CwaTideResult>>
        get() = _cwaTideResult

    private var _cwaTempResult = MutableLiveData<List<CwaTempResult>>()
    val cwaTempResult: LiveData<List<CwaTempResult>>
        get() = _cwaTempResult


    private var _cwaUviResult = MutableLiveData<List<CwaUviResult>>()
    val cwaUviResult: LiveData<List<CwaUviResult>>
        get() = _cwaUviResult
    fun getCwaTide(){
        viewModelScope.launch {
            try {
                val dataList = listOf(SurfinApi.retrofitService.getCwaTide())
                _cwaTideResult.value = dataList
                Log.i("Tide","Tide success: $dataList")
            }catch (e:Exception){
                Log.i("Tide","Tide:fail ${e.message}")
            }
        }
    }


    fun getCwaTemp(){
        viewModelScope.launch {
            try {
                val dataList = listOf(SurfinApi.retrofitService.getCwaTemp())
                _cwaTempResult.value = dataList
                Log.i("temp","temp success: $dataList")
            }catch (e:Exception){
                Log.i("temp","temp:fail ${e.message}")
            }
        }
    }

    fun getCwaWdsd(){
        viewModelScope.launch {
            try {
                val dataList = listOf(SurfinApi.retrofitService.getCwaWdsd())
                _cwaTempResult.value = dataList
                Log.i("wdsd","wdsd success: $dataList")
            }catch (e:Exception){
                Log.i("wdsd","wdsd:fail ${e.message}")
            }
        }
    }


    fun getCwaUvi(){
        viewModelScope.launch {
            try {
                val dataList = listOf(SurfinApi.retrofitService.getCwaUvi())
                _cwaUviResult.value = dataList
                Log.i("uvi","uvi success: $dataList")
            }catch (e:Exception){
                Log.i("uvi","uvi:fail ${e.message}")
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