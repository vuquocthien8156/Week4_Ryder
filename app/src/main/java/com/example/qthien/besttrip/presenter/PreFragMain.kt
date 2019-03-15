package com.example.qthien.besttrip.presenter

import android.content.Context
import android.util.Log
import com.example.qthien.besttrip.model.MoFragMain
import com.example.qthien.besttrip.view.fragment.IFragMain

class PreFragMain(var context: Context?, var iFragMain: IFragMain ) : IPreFragMain {
    var moFragMain: MoFragMain

    init {
        moFragMain = MoFragMain(context , this)
    }

    fun getLocation(){
        Log.d("TAGGG" , "Yess PreFragMain")
        moFragMain.getLocation()
    }

    override fun success(result: String , latitude : Double , longitude : Double) {
        iFragMain.success(result , latitude, longitude)
    }

    override fun failure(message: String) {
        iFragMain.failure(message)
    }
}