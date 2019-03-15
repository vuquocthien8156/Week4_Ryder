package com.example.qthien.besttrip

import com.example.qthien.besttrip.data.User

interface IListenerFragmentToMain {
    fun setVisibilityDrawerButton(b: Boolean)
    fun setVisibilityIconMap(b: Boolean)
    fun setTitleMain(title : String = "")
    fun setVisibilityLinearEdt(b: Boolean, nameAddress: String = "")
    fun setVisibilityIconCall(b : Boolean)
    fun setCheckedNavDrawer(id : Int)
    fun setLoginSuccess(user: User)
}