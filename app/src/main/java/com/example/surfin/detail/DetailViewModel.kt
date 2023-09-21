package com.example.surfin.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel(private val args: DetailDialogArgs) : ViewModel() {

    private val _selectedDetail = MutableLiveData<DetailDialogArgs>()
    val selectedDetail: LiveData<DetailDialogArgs>
        get() = _selectedDetail


    init {
        _selectedDetail.value = args
        Log.i("selectedProduct", "${args}")

    }


}