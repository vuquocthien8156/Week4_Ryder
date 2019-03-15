package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.data.Leg

interface IPreFragDeatilTrip {
    fun success(info : Leg)
    fun failure(message : String)
}