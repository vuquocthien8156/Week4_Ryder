package com.example.qthien.besttrip.view.fragment

import com.example.qthien.besttrip.data.Leg

interface IFragDetailTrip {
    fun success(info : Leg)
    fun failure(message : String)
}