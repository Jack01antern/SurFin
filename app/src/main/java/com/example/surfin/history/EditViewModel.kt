package com.example.surfin.history

import android.app.DatePickerDialog
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserActivityHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Exception

class EditViewModel(args: EditFragmentArgs, repository: SurfinRepository) : ViewModel() {


    fun updateHistory(history: UserActivityHistory, repository: SurfinRepository) {
        try {
            viewModelScope.launch {
//                withContext(Dispatchers.IO) {
                    repository.updateHistory(history)
                    Log.i("Edit ViewModel", "success: ${history}")
//                }
            }
        } catch (e: Exception) {
            Log.i("Edit ViewModel", "failed: ${e.message}")
        }
    }

    fun removeFromHistory(history: Long, repository: SurfinRepository) {
        viewModelScope.launch {
            try {
                repository.removeFromHistory(history)
                Log.i("edit page", "remove success $history")
            } catch (e: Exception) {
                Log.i("edit page", "remove failed ${e.message}")
            }
        }
    }

}