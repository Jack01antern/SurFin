package com.example.surfin.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserActivityHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddHistoryViewModel(private val repository: SurfinRepository) : ViewModel() {

    private var _activityHistory = MutableLiveData<List<UserActivityHistory>>()
    val activityHistory: LiveData<List<UserActivityHistory>>
        get() = _activityHistory


    fun addHistory(history: UserActivityHistory) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.insert(history)
                }
            } catch (e: Exception) {
                Log.i("db failed", "${e.message}")
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.clear()
                }
            } catch (e: Exception) {
                Log.i("db failed", "${e.message}")
            }
        }
    }


}