package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.data.Place
import com.example.qthien.besttrip.model.MoActiSeDes
import com.example.qthien.besttrip.view.fragment.IFragSelDes

class PreActiSeDes(var iActiSeDes: IFragSelDes) : IPreActiSeDes {

    var moActivity : MoActiSeDes
    init {
        moActivity = MoActiSeDes(this)
    }

    fun getDataBySearchPlace(params : Map<String , String>){
        moActivity.getDataBySearchPlace(params)
    }

    override fun success(arrResult: ArrayList<Place>) {
        iActiSeDes.success(arrResult)
    }

    override fun failure(message: String) {
        iActiSeDes.failure(message)
    }
}