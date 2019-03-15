package com.example.qthien.besttrip.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Address
import android.os.Bundle
import android.util.Log
import com.example.qthien.besttrip.presenter.IPreFragMain
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import android.widget.Toast
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.GoogleApiAvailability
import android.location.Geocoder
import java.io.IOException
import java.util.*
import android.content.IntentSender
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.google.android.gms.common.api.ResultCallback
import android.text.TextUtils
import com.google.android.gms.location.*


class MoFragMain(var context: Context?, var iPreFragMain: IPreFragMain ) : LocationListener{

    @SuppressLint("MissingPermission")
    fun getLocation(){
        val locationManager : LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isGPSEnable) {
            val mFusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context!!)
            mFusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val locationAddress = getAddress(it.latitude, it.longitude)
                    val address = locationAddress?.getAddressLine(0)

                    iPreFragMain.success("$address", it.latitude, it.longitude)
                } else
                    iPreFragMain.failure("Not found location")
            }
        }else{
            Toast.makeText(context , "Please enable GPS" , Toast.LENGTH_SHORT).show()
            return
        }
//        val locationManager : LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        if(isGPSEnable){
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 6000 , 10.toFloat() , this@MoFragMain)
//            val l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            if(l == null){
//                Log.d("TAGGG" , "location = null")
//            }
//            else{
//                Log.d("TAGGG" , "location != null")
//            }
//        }
//        else{
//            Toast.makeText(context , "Please enable GPS" , Toast.LENGTH_SHORT).show()
//            return
//        }
    }

    fun getAddress(latitude: Double, longitude: Double): Address? {
        Log.d("TAGGG" , "getAddress -- $latitude vs $longitude")
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(context, Locale.getDefault())

        try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location fragment_result to returned, by documents it recommended 1 to 5
            return addresses[0]

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null

    }

    override fun onLocationChanged(location: Location?) {
        Toast.makeText(context , "${location?.latitude} - ${location?.longitude}" , Toast.LENGTH_SHORT).show()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

}