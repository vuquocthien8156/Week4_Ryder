package com.example.qthien.besttrip.view.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qthien.besttrip.IMapMain
import com.example.qthien.besttrip.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

class FragmentMaps : SupportMapFragment(), IMapMain, OnMapReadyCallback {

    // callbackIMapMan
    override fun success(
        arrResult: ArrayList<LatLng>,
        arrPolyline: ArrayList<String>
    ) {

        for(p in arrPolyline){
            val po = PolylineOptions()
            po.addAll(PolyUtil.decode(p))
            po.width(5f)
            po.color(Color.RED)

            mMap.addPolyline(po)
        }
    }

    override fun failure(message: String) {
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
    }

    lateinit var mMap: GoogleMap
    var mapFragment : SupportMapFragment? = null

    var place1 : MarkerOptions? = null
    var place2 : MarkerOptions? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_maps , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        if(mapFragment != null){
            mapFragment?.getMapAsync(this)
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap : GoogleMap?) {
        mMap = googleMap!!

        // Add a marker in Sydney and move the camera
        val from = LatLng(10.8347538, 106.6877609)
        val to = LatLng(10.8702341 , 106.7444888)
        place1 = MarkerOptions().position(from).title("From")
        place2 = MarkerOptions().position(to).title("To")

        mMap.addMarker(place1)
        mMap.addMarker(place2)

        val cameraPosition = CameraPosition.Builder()
            .target(
                from
            )      // Sets the center of the map to location user
            .zoom(12f)                   // Sets the zoom
            .bearing(60f)                // Sets the orientation of the camera to east
            .tilt(70f)                   // Sets the tilt of the camera to 30 degrees
            .build()                   // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        Log.d("Vaoooooo" , "Vào khoongg ?")
    }


}