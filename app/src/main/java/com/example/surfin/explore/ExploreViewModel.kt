package com.example.surfin.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore


class ExploreViewModel(private val repository: SurfinRepository) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private var _spotsInfo = MutableLiveData(mutableListOf<Spots>())
    val spotsInfo: LiveData<MutableList<Spots>>
        get() = _spotsInfo

    init {
        getFirebase()
    }

    private fun getFirebase() {
        db.collection("spots").get().addOnSuccessListener { document ->

            if (document != null) {
                val spots = document.toObjects(Spots::class.java)
                _spotsInfo.postValue(spots.toMutableList())
            } else {
                Log.d("explore viewModel", "document is empty")
            }
            Log.i("explore viewModel", "spotsInfo: ${spotsInfo.value}")
        }.addOnFailureListener { e ->
            Log.d("explore viewModel", "get failed : ${e.message}")
        }
    }

}