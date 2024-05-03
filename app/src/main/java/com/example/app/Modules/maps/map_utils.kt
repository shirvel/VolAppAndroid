package com.example.app.Modules.maps

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

fun addressToLatLng(context: Context, address: String): LatLng? {
    val geocoder = Geocoder(context)
    try {
        val addresses: MutableList<Address>? = geocoder.getFromLocationName(address, 1)
        if (!addresses.isNullOrEmpty()) {
            val location = addresses[0]
            val latitude = location.latitude
            val longitude = location.longitude
            return LatLng(latitude, longitude)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}