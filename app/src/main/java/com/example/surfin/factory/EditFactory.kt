package com.example.surfin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfin.data.SurfinRepository
import com.example.surfin.history.EditFragmentArgs
import com.example.surfin.history.EditViewModel

class EditFactory (
    private val args: EditFragmentArgs,
    private val repository: SurfinRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(args,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
