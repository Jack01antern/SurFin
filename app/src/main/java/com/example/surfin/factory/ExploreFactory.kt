package com.example.surfin.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfin.MainViewModel
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.example.surfin.explore.ExploreViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ExploreFactory(private val firestore: FirebaseFirestore) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            return ExploreViewModel(firestore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}