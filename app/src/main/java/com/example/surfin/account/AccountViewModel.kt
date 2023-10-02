package com.example.surfin.account

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import kotlinx.coroutines.launch

class AccountViewModel (repository: SurfinRepository): ViewModel() {

    val userInfo = repository.getUserInfo()

    fun updateUserInfo(userInfo:UserInfo,repository: SurfinRepository){
        viewModelScope.launch {
            try {
                repository.updateUserInfo(userInfo)
            }catch (e:Exception){
                Log.i(" account user info","${e.message}")
            }
        }
    }
}