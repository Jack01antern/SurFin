package com.example.surfin.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.TideTime
import kotlinx.coroutines.launch
import java.security.KeyStore.Entry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherViewModel(
    private val args: WeatherFragmentArgs,
    private val repository: SurfinRepository
) : ViewModel() {

    private val apiKey = com.example.surfin.BuildConfig.API_KEY

    private var _cwaTideResult = MutableLiveData<List<com.github.mikephil.charting.data.Entry>>()
    val cwaTideResult: LiveData<List<com.github.mikephil.charting.data.Entry>>
        get() = _cwaTideResult

    private var _cwaTempResult = MutableLiveData<String>()
    val cwaTempResult: LiveData<String>
        get() = _cwaTempResult

    private var _cwaWdsdResult = MutableLiveData<String>()
    val cwaWdsdResult: LiveData<String>
        get() = _cwaWdsdResult

    private var _cwaUviResult = MutableLiveData<String>()
    val cwaUviResult: LiveData<String>
        get() = _cwaUviResult

    private var _cwaWeatherResult = MutableLiveData<String>()
    val cwaWeatherResult: LiveData<String>
        get() = _cwaWeatherResult

    private fun List<TideTime>.toEntryList(): List<com.github.mikephil.charting.data.Entry> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        return this.map {
            val dateTime = dateFormat.parse(it.dateTime)
            val dateTimeMillis = dateTime.time.toFloat()
            com.github.mikephil.charting.data.Entry(
                dateTimeMillis,
                it.tideHeights.aboveTWVD.toFloat()
            )
        }
    }


    fun getCwaTide() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTide(apiKey, args.tempId.tideStationId)
                val entry =
                    dataList.records.tideForecasts[0].location.timePeriods.daily[0].tideTime.toEntryList()
                _cwaTideResult.value = entry
            } catch (e: Exception) {
                Log.i("cwa", "tide:fail ${e.message}")
            }
        }
    }


    fun getCwaTemp() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTemp(apiKey, args.tempId.tempStationId)
                _cwaTempResult.value = dataList.records.location[0].weatherElement[0].elementValue
                Log.i("cwa", "temp success: ${_cwaTempResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "temp:fail ${e.message}")
            }
        }
    }


    fun getCwaWdsd() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWdsd(apiKey, args.tempId.wdsdStationId)
                _cwaWdsdResult.value = dataList.records.location[0].weatherElement[0].elementValue
                Log.i("cwa", "wdsd success: ${_cwaWdsdResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "wdsd:fail ${e.message}")
            }
        }
    }


    fun getCwaUvi() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaUvi(
                    apiKey,
                    args.tempId.uviStationId
                )
                _cwaUviResult.value = dataList.records.weatherElement.location[0].value.toString()
                Log.i("cwa", "uvi success: ${_cwaUviResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "uvi:fail ${e.message}")
            }
        }
    }


    private fun getCwaWeather() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWeather(
                    apiKey,
                    args.tempId.weatherStationId
                )
                _cwaWeatherResult.value =
                    dataList.records.location[0].weatherElement[0].elementValue
                Log.i("cwa", "weather success: ${_cwaWeatherResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "weather:fail ${e.message}")
            }
        }
    }

    fun parseDateTime(dateTimeString: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.TAIWAN)
        return format.parse(dateTimeString)
    }


    init {
        getCwaTemp()
        getCwaTide()
        getCwaWdsd()
        getCwaUvi()
        getCwaWeather()
    }
}



