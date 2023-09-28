package com.example.surfin.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class ZoomViewModel(args: String) : ViewModel() {

    private var _photoUrl = MutableLiveData<String>()
    val photoUrl: LiveData<String>
        get() = _photoUrl


    init {
        args?.let {
            _photoUrl.value = it
            Log.i("photo", "${_photoUrl.value}")
        }
    }

}