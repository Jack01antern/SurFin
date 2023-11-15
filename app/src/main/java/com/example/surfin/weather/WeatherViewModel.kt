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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class WeatherViewModel(
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


    private var _cwaWaveResult = MutableLiveData<String>()
    val cwaWaveResult: LiveData<String>
        get() = _cwaWaveResult

    fun List<TideTime>.toEntryList(): List<com.github.mikephil.charting.data.Entry> {
        return this.map {
            val dateTime = stringToDate(it.dateTime)
            val dateTimeMillis = dateToMillis(dateTime)
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
                Log.i("cwa", "$dataList")

                val entry =
                    dataList.records.tideForecasts[0].location.timePeriods.daily[0].tideTime.toEntryList()
                _cwaTideResult.value = entry
                Log.i("cwa", "$entry")
            } catch (e: Exception) {
                Log.i("cwa", "tide:fail ${e.message}")
            }
        }
    }


    fun getCwaTemp() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTemp(apiKey, args.tempId.tempStationId)
                _cwaTempResult.value = dataList.records.station[0].weatherElement.airTemperature.toString()
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
                _cwaWdsdResult.value = dataList.records.station[0].weatherElement.windSpeed.toString()
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
                _cwaUviResult.value = dataList.records.weatherElement.location[0].uvIndex.toString()
                Log.i("cwa", "uvi success: ${_cwaUviResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "uvi:fail ${e.message}")
            }
        }
    }


    fun getCwaWeather() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWeather(
                    apiKey,
                    args.tempId.weatherStationId
                )
                _cwaWeatherResult.value =
                    dataList.records.station[0].weatherElement.weather
                Log.i("cwa", "weather success: ${_cwaWeatherResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "weather:fail ${e.message}")
            }
        }
    }

    fun getCwaWave() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWave(
                    apiKey,
                    args.tempId.waveStationId
                )
                _cwaWaveResult.value =
                    dataList.records.seaSurfaceObs.location[0].stationObsTimes.stationObsTime.last().weatherElements.waveHeight
                Log.i("cwa", "wave success: ${_cwaWaveResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "wave:fail ${e.message}")
            }
        }
    }


    init {
        getCwaTemp()
        getCwaTide()
        getCwaWdsd()
        getCwaUvi()
        getCwaWeather()
        getCwaWave()
    }

    companion object {
        const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        private val formatter = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault())

        fun stringToDate(dateString: String): Date {
            return formatter.parse(dateString) ?: throw ParseException("Invalid date format", 0)
        }

        fun dateToMillis(date: Date): Float {
            return date.time.toFloat()
        }
    }
}



