package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.data.User

interface IPreFragLogin {
    fun success(user: User?)
    fun failure(message : String)
}