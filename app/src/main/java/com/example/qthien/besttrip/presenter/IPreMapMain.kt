package com.example.qthien.besttrip.presenter

import com.google.android.gms.maps.model.LatLng

interface IPreMapMain {
    fun success(
        arrResult: ArrayList<LatLng>,
        arrPolyline: ArrayList<String>
    )
    fun failure(message : String)
}