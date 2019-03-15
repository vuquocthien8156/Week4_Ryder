package com.example.qthien.besttrip.model

import com.example.qthien.besttrip.data.User
import com.example.qthien.besttrip.presenter.IPreFragRegister
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.math.BigInteger
import java.security.MessageDigest


class MoFragRegister(var iPreFragRegister: IPreFragRegister) {
    fun register(user : User , activity: Activity){
        val userr = User(user.username!! , user.email!! , user.password!!)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email!! , user.password!!)
            .addOnCompleteListener(activity){ task ->
                if (task.isSuccessful) {
                     addUser(userr)
                } else {
                    Log.d("ErrorRegister" , task.exception.toString())
                    iPreFragRegister.failure("fail")
                }
            }
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    fun addUser(user : User){
        user.id = FirebaseAuth.getInstance().currentUser!!.uid
        user.password = user.password?.md5()
        FirebaseDatabase.getInstance().getReference("User")
            .child(user.id!!)
            .setValue(user).addOnCompleteListener({
                if(it.isSuccessful){
                    iPreFragRegister.success("success")
                }
                else{
                    iPreFragRegister.failure(it.exception.toString())
                }
            })
    }
}