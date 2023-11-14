package com.example.surfin.explore

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.surfin.MainViewModel
import com.example.surfin.R
import com.example.surfin.data.Spots
import com.example.surfin.factory.ExploreFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore


private const val REQUEST_CODE_LOCATION_PERMISSION = 0x00
private const val ZOOM_IN = 8F


class ExploreFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var viewModel: ExploreViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var map: GoogleMap? = null

    val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        //mock data for zoom in
        val center = LatLng(23.716, 121.0564)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, ZOOM_IN))
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

        val firestore = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this,ExploreFactory(firestore)).get(ExploreViewModel::class.java)

        viewModel.spotsInfo.observe(viewLifecycleOwner) { spots ->
            spots.forEach { spot ->
                val latLong = LatLng(spot.lat, spot.longitude)
                map?.addMarker(
                    MarkerOptions().position(latLong).title(spot.title)
                )
            }

            if (spots.isNotEmpty()) {
                setMarkerClickListener(spots,mainViewModel)
            }
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.explore) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map = null
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
    }

    private fun setMarkerClickListener(spots: MutableList<Spots>, viewModel: MainViewModel) {
        map?.setOnMarkerClickListener { marker ->
            val selectedSpot = marker.title?.let { findSpotByTitle(spots, it) }
            if (selectedSpot != null) {
                viewModel.selectedSpotDetail = selectedSpot
            }

            navigateToDetailFragment(selectedSpot)
            Log.i("explore fragment", "safeArgs: ${selectedSpot}")
            true
        }
    }


    private fun findSpotByTitle(spots: List<Spots>, title: String): Spots? {
        for (spot in spots) {
            if (spot.title == title) {
                return spot
            }
        }
        return null
    }

    private fun navigateToDetailFragment(spot: Spots?) {
        spot?.let {
            findNavController().navigate(
                ExploreFragmentDirections.actionNavigateToDetailFragment(it)
            )
        }
    }
}
