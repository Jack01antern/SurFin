package com.example.surfin.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
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

    private var _cwaTideResult = MutableLiveData<MutableList<Entry>>()
    val cwaTideResult: LiveData<MutableList<Entry>>
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


    private fun getCwaTide() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTide(apiKey, args.tempId.tideStationId)
                val dailyDataList = mutableListOf<Entry>()

                dataList.records.tideForecasts[0].location.timePeriods.daily.take(3).forEach {


                    it.tideTime.forEach { it ->
                        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.TAIWAN)
                        val dateTime = format.parse(it.dateTime).time.toFloat()


                        val aboveTWVD = it.tideHeights.aboveTWVD.toFloat()

                    }
                    _cwaTideResult.value = dailyDataList

                }

                Log.i("cwa", "tide success: ${_cwaTideResult.value}")

            } catch (e: Exception) {
                Log.i("cwa", "tide:fail ${e.message}")
            }
        }
    }


    private fun getCwaTideDate() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTide(apiKey, args.tempId.tideStationId)
                val dateList = mutableListOf<String>()
                dataList.records.tideForecasts[0].location.timePeriods.daily[0].tideTime.forEach {
                    dateList.add(it.dateTime)
                }
//                _cwaDateResult.value = dateList
                Log.i("cwa", "tide success: ${_cwaTideResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "tide:fail ${e.message}")
            }
        }
    }

    private fun getCwaTemp() {
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


    private fun getCwaWdsd() {
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


    private fun getCwaUvi() {
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
        getCwaTideDate()
    }
}