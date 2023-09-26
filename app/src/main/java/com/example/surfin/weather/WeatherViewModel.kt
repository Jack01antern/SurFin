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
import com.example.surfin.data.TideLocation
import com.example.surfin.data.TideStationObsTime
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherViewModel(
    private val args: WeatherFragmentArgs,
    private val repository: SurfinRepository
) : ViewModel() {

    private val apiKey = com.example.surfin.BuildConfig.API_KEY

    private var _cwaTideResult = MutableLiveData<List<TideStationObsTime>>()
    val cwaTideResult: LiveData<List<TideStationObsTime>>
        get() = _cwaTideResult

    private var _cwaTempResult = MutableLiveData<CwaTempResult>()
    val cwaTempResult: LiveData<CwaTempResult>
        get() = _cwaTempResult

    private var _cwaWdsdResult = MutableLiveData<CwaTempResult>()
    val cwaWdsdResult: LiveData<CwaTempResult>
        get() = _cwaWdsdResult

    private var _cwaUviResult = MutableLiveData<CwaUviResult>()
    val cwaUviResult: LiveData<CwaUviResult>
        get() = _cwaUviResult

    private var _cwaWeatherResult = MutableLiveData<CwaTempResult>()
    val cwaWeatherResult: LiveData<CwaTempResult>
        get() = _cwaWeatherResult

//    private var _cwaEarthquakeResult = MutableLiveData<List<CwaEarthquakeResult>>()
//    val cwaEarthquakeResult: LiveData<List<CwaEarthquakeResult>>
//        get() = _cwaEarthquakeResult

    private fun getCwaTide() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTide(apiKey,args.tempId.tideStationId)
                _cwaTideResult.value = dataList.records.seaSurfaceObs.location[0].stationObsTimes.stationObsTime
                Log.i("cwa", "tide success: ${_cwaTideResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "tide:fail ${e.message}")
            }
        }
    }


    private fun getCwaTemp() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTemp(apiKey,args.tempId.tempStationId)
                _cwaTempResult.value = dataList
                Log.i("cwa", "temp success: ${_cwaTempResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "temp:fail ${e.message}")
            }
        }
    }


    private fun getCwaWdsd() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWdsd(apiKey,args.tempId.wdsdStationId)
                _cwaWdsdResult.value = dataList
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
                _cwaUviResult.value = dataList
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
                _cwaWeatherResult.value = dataList
                Log.i("cwa", "weather success: ${_cwaWeatherResult.value}")
            } catch (e: Exception) {
                Log.i("cwa", "weather:fail ${e.message}")
            }
        }
    }


//    private fun sortTideLocationsByTime(tideLocations: List<TideLocation>): List<TideLocation> {
//        // 定义日期格式
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//
//        // 使用 sortedBy 函数按时间排序
//        return tideLocations.sortedBy { location ->
//            // 将时间字符串解析为 Date 对象
//            val date = dateFormat.parse(location.time.obsTime)
//
//            // 返回 Date 对象以进行排序
//            date
//        }
//    }
//
//    fun main() {
//        // 示例 TideLocation 列表
//        val tideLocations = listOf(
//            TideLocation("24.778333", "121.494583", "福山", "C0A560", TideTime("2023-09-24 16:00:00"), emptyList(), emptyList()),
//            TideLocation("22.994972", "120.144111", "安平", "C0X190", TideTime("2023-09-24 16:00:00"), emptyList(), emptyList()),
//            // 添加更多 TideLocation
//        )
//
//        // 按时间排序 TideLocation
//        val sortedLocations = sortTideLocationsByTime(tideLocations)
//
//        // 打印排序后的结果
//        sortedLocations.forEach { location ->
//            println("Location: ${location.locationName}, Time: ${location.time.obsTime}")
//        }
//    }

//    private fun getCwaEarthquake() {
//        viewModelScope.launch {
//            try {
//                val dataList = repository.getCwaEarthquake(args.tempId.)
//                _cwaEarthquakeResult.value = listOf(dataList)
//                Log.i("cwa", "earthquake success: $dataList")
//            } catch (e: Exception) {
//                Log.i("cwa", "earthquake:fail ${e.message}")
//            }
//        }
//    }

    init {
        getCwaTemp()
        getCwaTide()
        getCwaWdsd()
        getCwaUvi()
        getCwaWeather()
//        getCwaEarthquake()
    }
}