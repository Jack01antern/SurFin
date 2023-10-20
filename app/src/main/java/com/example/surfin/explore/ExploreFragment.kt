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

        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this,
            ExploreFactory(repository)
        ).get(ExploreViewModel::class.java)

        viewModel.spotsInfo.observe(viewLifecycleOwner) { spots ->
            spots.forEach { spot ->
                val latLong = LatLng(spot.lat, spot.longitude)
                map?.addMarker(
                    MarkerOptions().position(latLong).title(spot.title)
                )
            }

            if (spots.isNotEmpty()) {
                setMarkerClickListener(spots)
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
    private fun setMarkerClickListener(spots: MutableList<Spots>) {
        map?.setOnMarkerClickListener { marker ->
            var safeArgs = Spots()
            for (spot in spots) {
                if (spot.title == marker.title) {
                    safeArgs = spot
                    mainViewModel.selectedSpotDetail = safeArgs
                    Log.i("explore fragment", "Main ViewModel:${mainViewModel.selectedSpotDetail}")
                    break
                }
            }

            findNavController().navigate(
                ExploreFragmentDirections.actionNavigateToDetailFragment(safeArgs)
            )
            Log.i("explore fragment", "safeArgs: ${safeArgs}")
            true
        }
    }
}
