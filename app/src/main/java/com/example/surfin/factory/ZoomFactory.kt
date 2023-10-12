package com.example.surfin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfin.detail.ZoomViewModel

class ZoomFactory(
    private val args: String
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ZoomViewModel::class.java)) {
            return ZoomViewModel(args) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
