package com.example.surfin.explore

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore


private const val ZOOM_IN = 10F
class ExploreViewModel(private val repository: SurfinRepository) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    var spotsInfo = mutableListOf<Spots>()

    val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val school = LatLng(25.0385, 121.5324)
        googleMap.addMarker(
            MarkerOptions().position(school).title("Marker in School")
                .snippet("The Best School Ever")
        )

        try {
            repository.observeFirebaseSpotData()
            db.collection("spots").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("retrieve??", e)
                    return@addSnapshotListener
                }
                googleMap.clear()
                spotsInfo.clear()
                spotsInfo.addAll(snapshot!!.toObjects(Spots::class.java))

                for (spot in spotsInfo) {
                    val latLong = LatLng(spot.lat, spot.long)
                    googleMap.addMarker(
                        MarkerOptions().position(latLong).title(spot.title).snippet(spot.content)
                    )
                }
                Log.i("retrieve!!", " MSG: $spotsInfo")
            }

        } catch (e: Exception) {
            Log.i("retrieve failed", " MSG: ${e.message}")
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, ZOOM_IN))
    }

    fun observeFirebaseData() {
        try {

        } catch (e: Exception) {
            Log.i("retrieve failed", " MSG: ${e.message}")
        }
    }


}