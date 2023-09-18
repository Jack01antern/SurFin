package com.example.surfin.explore

import androidx.lifecycle.ViewModel
import com.example.surfin.data.SurfinRepository


class ExploreViewModel(private val db: SurfinRepository) : ViewModel() {

    fun getFirebaseSpotInfo(){
        db.getFirebaseSpotInfo()
    }

}