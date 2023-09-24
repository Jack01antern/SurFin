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

class WeatherViewModel(
    private val args: WeatherFragmentArgs,
    private val repository: SurfinRepository
) : ViewModel() {

    private var _cwaTideResult = MutableLiveData<List<CwaTideResult>>()
    val cwaTideResult: LiveData<List<CwaTideResult>>
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

//    private var _cwaEarthquakeResult = MutableLiveData<List<CwaEarthquakeResult>>()
//    val cwaEarthquakeResult: LiveData<List<CwaEarthquakeResult>>
//        get() = _cwaEarthquakeResult

    private fun getCwaTide() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaTide("CWB-35714F4C-C162-403B-BF09-664E4C82C664",args.tempId.tideStationId)
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
                val dataList = repository.getCwaTemp("CWB-35714F4C-C162-403B-BF09-664E4C82C664",args.tempId.tempStationId)
                _cwaTempResult.value = dataList
                Log.i("cwa", "temp success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "temp:fail ${e.message}")
            }
        }
    }


    private fun getCwaWdsd() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaWdsd("CWB-35714F4C-C162-403B-BF09-664E4C82C664",args.tempId.wdsdStationId)
                _cwaWdsdResult.value = dataList
                Log.i("cwa", "wdsd success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "wdsd:fail ${e.message}")
            }
        }
    }


    private fun getCwaUvi() {
        viewModelScope.launch {
            try {
                val dataList = repository.getCwaUvi(
                    "CWB-35714F4C-C162-403B-BF09-664E4C82C664",
                    args.tempId.uviStationId
                )
                _cwaUviResult.value = dataList
                Log.i("cwa", "uvi success: $dataList")
            } catch (e: Exception) {
                Log.i("cwa", "uvi:fail ${e.message}")
            }
        }
    }


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
//        getCwaEarthquake()

    }
}