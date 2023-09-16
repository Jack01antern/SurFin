package com.example.surfin.explore

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.surfin.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


private const val REQUEST_CODE_LOCATION_PERMISSION = 0x00

class ExploreFragment : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
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
        googleMap.addMarker(MarkerOptions().position(school).title("Marker in School").snippet("The Best School Ever"))

        val mcCauleyBeach = LatLng(25.21014, 121.66092)
        googleMap.addMarker(MarkerOptions().position(mcCauleyBeach).title("McCauley Beach").snippet("The Most Famous Spot in North"))

        val baiShaWan = LatLng(25.28496, 121.51806)
        googleMap.addMarker(MarkerOptions().position(baiShaWan).title("Bai Sha Bay").snippet("24hr surveillance: https://youtu.be/St4GHsJzfg4"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school,10f))

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

        val options = GoogleMapOptions()
            .mapId(getString(R.string.map_id))

//        val mapFragment = SupportMapFragment.newInstance(options)
//
//        childFragmentManager.beginTransaction()
//            .replace(R.id.explore, mapFragment) // R.id.mapContainer 应该是包含地图的布局元素
//            .commit()
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
