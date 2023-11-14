package com.example.surfin.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserActivityHistory
import kotlinx.coroutines.launch

class HistoryViewModel(repository: SurfinRepository) : ViewModel() {
    val activityHistory: LiveData<List<UserActivityHistory>> = repository.getAllHistory()

}