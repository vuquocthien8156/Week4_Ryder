package com.example.qthien.besttrip

import com.example.qthien.besttrip.data.Leg
import com.google.android.gms.maps.model.LatLng

interface IMapMain {
    fun success(
        arrResult: ArrayList<LatLng>,
        arrPolyline: ArrayList<String>)
    fun failure(message : String)
}