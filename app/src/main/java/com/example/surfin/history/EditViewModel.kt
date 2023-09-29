package com.example.surfin.history

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import kotlinx.coroutines.launch

class EditViewModel(args: EditFragmentArgs, repository: SurfinRepository) : ViewModel() {




    fun get(args: EditFragmentArgs, repository: SurfinRepository) {

        viewModelScope.launch {
//            repository.getCertainHistory(args.historyInfo.activityId)
        }
    }


}