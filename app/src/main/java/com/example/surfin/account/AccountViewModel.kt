package com.example.surfin.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.CwaTideResult
import com.example.surfin.network.SurfinApi
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private var _cwaTideResult = MutableLiveData<List<CwaTideResult>>()
    val cwaTideResult: LiveData<List<CwaTideResult>>
        get() = _cwaTideResult

    fun getCwaTide(){
        viewModelScope.launch {
            try {
                Log.i("Tide","Tide: called")
                val dataList = listOf(SurfinApi.retrofitService.getCwaTide())
                _cwaTideResult.value = dataList
                Log.i("Tide","Tide success: $dataList")
            }catch (e:Exception){
                Log.i("Tide","Tide:fail ${e.message}")
            }
        }
    }
}