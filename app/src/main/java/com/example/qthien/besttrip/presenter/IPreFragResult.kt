package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.data.Taxi

interface IPreFragResult {
    fun success(listTaxi : List<Taxi>)
    fun failure(message : String)
}