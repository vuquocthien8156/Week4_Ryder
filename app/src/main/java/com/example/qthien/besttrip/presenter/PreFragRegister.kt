package com.example.qthien.besttrip.presenter

import android.app.Activity
import com.example.qthien.besttrip.data.User
import com.example.qthien.besttrip.model.MoFragRegister
import com.example.qthien.besttrip.view.fragment.IFragRegister

class PreFragRegister(var iFragRegister: IFragRegister) : IPreFragRegister{

    fun register(user : User , activity : Activity){
        MoFragRegister(this).register(user , activity)
    }

    override fun success(message: String) {
        iFragRegister.success(message)
    }

    override fun failure(message: String) {
        iFragRegister.failure(message)
    }
}