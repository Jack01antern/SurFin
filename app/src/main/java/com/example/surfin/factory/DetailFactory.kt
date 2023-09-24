package com.example.surfin.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfin.data.SurfinRepository
import com.example.surfin.detail.DetailDialogArgs
import com.example.surfin.detail.DetailViewModel

class DetailFactory(private val repository: SurfinRepository,
    private val args: DetailDialogArgs
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository,args) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
