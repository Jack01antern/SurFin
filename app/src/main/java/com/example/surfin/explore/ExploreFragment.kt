package com.example.surfin.explore

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.data.Spots
import com.example.surfin.factory.ExploreFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore


private const val REQUEST_CODE_LOCATION_PERMISSION = 0x00
private const val ZOOM_IN = 10F


class ExploreFragment : Fragment() {

    private lateinit var viewModel: ExploreViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val db = FirebaseFirestore.getInstance()
    var spotsInfo = mutableListOf<Spots>()
    private val callback = OnMapReadyCallback { googleMap ->
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fetchLocation()


        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this,
            ExploreFactory(repository)
        ).get(ExploreViewModel::class.java)


        val mapFragment = childFragmentManager.findFragmentById(R.id.explore) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }


    //get user's current location
    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    "${it.latitude}${it.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
