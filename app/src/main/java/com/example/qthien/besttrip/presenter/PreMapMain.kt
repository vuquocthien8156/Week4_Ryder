package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.IMapMain
import com.example.qthien.besttrip.model.MoMapMain
import com.google.android.gms.maps.model.LatLng

class PreMapMain(var iMapMain : IMapMain) : IPreMapMain{

    fun getListStep(params : Map<String, String>){
        val moMapMain = MoMapMain(this)
        moMapMain.getListStep(params)
    }

    override fun failure(message : String) {
        iMapMain.failure(message)
    }

    override fun success(
        arrResult: ArrayList<LatLng>,
        arrPolyline: ArrayList<String>
    ) {
        iMapMain.success(arrResult , arrPolyline)
    }
}