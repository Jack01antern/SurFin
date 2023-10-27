package com.example.surfin.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surfin.data.Spots
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class ExploreViewModel(private val firestore: FirebaseFirestore) : ViewModel() {

    private var _spotsInfo = MutableLiveData(mutableListOf<Spots>())
    val spotsInfo: LiveData<MutableList<Spots>>
        get() = _spotsInfo

    init {
        getFirebase()
    }

    private fun getFirebase() {
        firestore.collection("spots").get()
            .addOnSuccessListener(this::handleSuccess)
            .addOnFailureListener(this::handleFailure)
    }

    private fun handleSuccess(document: QuerySnapshot?) {
        document?.let {
            val spots = it.toObjects(Spots::class.java)
            _spotsInfo.postValue(spots.toMutableList())
            Log.i("explore viewModel", "spotsInfo: ${spotsInfo.value}")
        }
    }

    private fun handleFailure(e: Exception) {
        Log.d("explore viewModel", "get failed : ${e.message}")
    }
}