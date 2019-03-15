package com.example.qthien.besttrip.view.fragment

import com.example.qthien.besttrip.data.Taxi

interface IFragResult {
    fun success(listTaxi : List<Taxi>)
    fun failure(message : String)
}