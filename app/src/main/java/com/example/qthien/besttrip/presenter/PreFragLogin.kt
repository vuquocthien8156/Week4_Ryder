package com.example.qthien.besttrip.presenter

import android.app.Activity
import com.example.qthien.besttrip.data.User
import com.example.qthien.besttrip.model.MoFragLogin
import com.example.qthien.besttrip.view.fragment.IFragLogin
import com.google.firebase.auth.FirebaseAuth

class PreFragLogin(var iFragLogin: IFragLogin) : IPreFragLogin {
    override fun success(user: User?) {
        iFragLogin.success(user)
    }

    override fun failure(message: String) {
        iFragLogin.failure(message)
    }

    fun login(mAuth: FirebaseAuth, activity: Activity, email: String, password: String) {
        MoFragLogin(this).login(mAuth , activity , email , password)
    }

    fun getUser(id : String){
        MoFragLogin(this).getUser(id)
    }
}