package com.example.app.Modules.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.model.Post
import com.example.app.model.PostListModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException


class vol_map : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var currentLocationMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vol_map, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        // Get and display current location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    addMarkerForLocation(currentLatLng, "Your Location")

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Unable to fetch current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        loadAllPostsLocationsToMap();

        // adding on click listener to marker of google maps.
        mMap.setOnMarkerClickListener { marker ->
            onMarkerClick(marker)
        }
    }

    fun addMarkerForAddress(address: String, title: String){
        val location = addressToLatLng(requireContext(), address)
        Log.i("TAG", "The location address: $location")
        if (location != null){
            addMarkerForLocation(location, title)
        }
    }


    fun addMarkerForLocation(location: LatLng, title: String){

        currentLocationMarker = mMap.addMarker(MarkerOptions().position(location))
        currentLocationMarker?.setTitle(title)
    }

    fun getAddress(marker: Marker?): String {
        Log.i("TAG", "Getting the address")
        val geocoder = Geocoder(requireContext())
        val markerLatLng = marker?.position
        if (markerLatLng == null) {
            return "Marker as no position"
        }
        try {
            val addresses =
                geocoder.getFromLocation(markerLatLng.latitude, markerLatLng.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0].getAddressLine(0)

                    return address

                }

            }

        } catch (e: IOException) {
            e.printStackTrace()
            return "Error"
        }
        return "Location not found"
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // which is clicked and displaying it in a toast message.
        val markerName = marker.title
        Toast.makeText(requireContext(), "Clicked location is $markerName", Toast.LENGTH_SHORT)
            .show()

        if (markerName != "Your Location"){
            val safarisValue = markerName // Example Writer
            val bundle = bundleOf("postId" to safarisValue)
            findNavController().navigate(R.id.action_vol_map_to_post, bundle)
        }
        return true
    }

    fun loadAllPostsLocationsToMap(){
        val allPostsLiveData = PostListModel.instance.getAllPosts()
        Log.i("TAG", "the posts test")
        allPostsLiveData.observe(viewLifecycleOwner){allPosts ->
            Log.i("TAG", "the posts: ${allPosts}")
            if (allPosts != null) {
                for (post in allPosts){
                    if (post.address.isNotEmpty()){
                        addMarkerForAddress(post.address, post.title)
                        Log.i("TAG", "add post with address: ${post.address}")
                    }
                }
            }else{
//                addMarkerForAddress("hagibor haalmoni 50", "id of post")
            }
        }


    }





}