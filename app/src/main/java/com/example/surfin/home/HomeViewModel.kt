package com.example.surfin.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.CwaTideResult
import com.example.surfin.network.SurfinApi
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var _cwaTideResult = MutableLiveData<List<CwaTideResult>>()
    val cwaTideResult: LiveData<List<CwaTideResult>>
        get() = _cwaTideResult

    fun getCwaTide(){
        viewModelScope.launch {
            try {
                val dataList = SurfinApi.retrofitService.getCwaTide()
                Log.i("Tide","Tide: $dataList")
            }catch (e:Exception){
                Log.i("Tide","Tide: ${e.message}")
            }
        }
    }

    init {
        getCwaTide()
    }
}