package com.example.surfin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surfin.data.Spots
import com.example.surfin.util.CurrentFragment

class MainViewModel :ViewModel(){
    val currentFragment = MutableLiveData<CurrentFragment>()
    var selectedSpotDetail = Spots()
}