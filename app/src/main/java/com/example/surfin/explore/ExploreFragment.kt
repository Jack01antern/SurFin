package com.example.surfin.explore

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.data.Spots
import com.example.surfin.factory.ExploreFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore


private const val REQUEST_CODE_LOCATION_PERMISSION = 0x00
private const val ZOOM_IN = 8F


class ExploreFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var spotsInfo = mutableListOf<Spots>()
    private lateinit var viewModel: ExploreViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val callback = OnMapReadyCallback { googleMap ->

        //mock data for zoom in
        val center = LatLng(23.716, 121.0564)
        googleMap.addMarker(
            MarkerOptions().position(center).title("Marker in School")
                .snippet("The Best School Ever")
        )


        //get data from firebase and add marker
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
                    val latLong = LatLng(spot.lat, spot.longitude)
                    googleMap.addMarker(
                        MarkerOptions().position(latLong).title(spot.title).snippet(spot.content)
                    )
                }
                Log.i("retrieve!!", " MSG: $spotsInfo")
            }

        } catch (e: Exception) {
            Log.i("retrieve failed", " MSG: ${e.message}")
        }

        var safeArgs = Spots()

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, ZOOM_IN))
        googleMap.setOnMarkerClickListener {
            for (spot in spotsInfo) {
                if (spot.title == it.title) {
                    safeArgs = spot
                }
            }

            findNavController().navigate(
                ExploreFragmentDirections.actionNavigateToDetailFragment(
                    safeArgs
                )
            )
            Log.i("explore", "${safeArgs}")
            true
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

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


//    override fun onDestroyView() {
//        super.onDestroyView()
//        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//
//    }


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
//        task.addOnSuccessListener {
//            if (it != null) {
//                Toast.makeText(
//                    requireContext(),
//                    "${it.latitude}${it.longitude}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }
}
