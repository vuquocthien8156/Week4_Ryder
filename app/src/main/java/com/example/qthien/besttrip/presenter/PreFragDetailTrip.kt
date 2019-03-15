package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.data.Leg
import com.example.qthien.besttrip.model.MoDetailTrip
import com.example.qthien.besttrip.view.fragment.IFragDetailTrip

class PreFragDetailTrip(var iFragDetailTrip: IFragDetailTrip): IPreFragDeatilTrip {

    fun getInfoTrip(params : Map<String , String>){
        MoDetailTrip(this).getInfo(params)
    }

    override fun success(info: Leg) {
        iFragDetailTrip.success(info)
    }

    override fun failure(message: String) {
        iFragDetailTrip.failure(message)
    }
}