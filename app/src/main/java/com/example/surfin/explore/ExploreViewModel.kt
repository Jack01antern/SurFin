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

    private var _googleMap = MutableLiveData<GoogleMap>()
    val googleMap: LiveData<GoogleMap>
        get() = _googleMap


    fun getFirebase(googleMap: GoogleMap) {
        db.collection("spots").get().addOnSuccessListener { document ->
            Log.i("explore", "spots info1: ${document.documents}")
            if (document != null) {
                Log.i("explore", "spots info2: ${document.documents}")
                _spotsInfo.value?.addAll(document.toObjects(Spots::class.java))
                for (spot in _spotsInfo.value!!) {
                    val latLong = LatLng(spot.lat, spot.longitude)
                    googleMap.addMarker(
                        MarkerOptions().position(latLong)
                    )
                }
            } else {
                Log.d("explore", "document is empty")
            }
        }.addOnFailureListener { e ->
            Log.d("explore", "get failed : ${e.message}")
        }
    }

}