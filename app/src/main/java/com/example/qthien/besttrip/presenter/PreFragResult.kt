package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.model.MoFragResult
import com.example.qthien.besttrip.view.fragment.IFragResult

class PreFragResult(var iFragResult : IFragResult) : IPreFragResult {

    fun getAllData(){
        MoFragResult(this).getAllData()
    }

    override fun success(listTaxi: List<Taxi>) {
        iFragResult.success(listTaxi)
    }

    override fun failure(message: String) {
        iFragResult.failure(message)
    }
}