package com.example.surfin.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class HomeViewModel(private val repository: SurfinRepository) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private var _fireResult = MutableLiveData<List<Spots>>()
    val fireResult: LiveData<List<Spots>>
        get() = _fireResult

    fun getFirebase() {
        db.collection("spots")
            .get()
            .addOnSuccessListener { results ->
                val resultList = mutableListOf<Spots>()
                for (document in results) {
                    val result = document.toObject<Spots>()
                    resultList.add(result)
                }
                _fireResult.value = resultList
//                Log.d("Fire store", "resultList:  ${_fireResult.value}")
            }
            .addOnFailureListener { exception ->
                Log.w("Fire store", "Error getting documents.", exception)
            }
    }


    fun searchFirebase(spotName: String) {
        db.collection("spots")
            .whereEqualTo("title", spotName)
            .get()
            .addOnSuccessListener { documents ->
                val resultList = mutableListOf<Spots>()
                for (document in documents) {
                    Log.d("Fire store", "search document:  ${document.data}")
                    val result = document.toObject<Spots>()
                    resultList.add(result)
                    Log.d("Fire store", "search resultList:  ${resultList}")
                }
                _fireResult.value = resultList
                Log.d("Fire store", "search fireResult:  ${_fireResult.value}")
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents: ", exception)
            }
    }

    init {
        getFirebase()
    }
}