package com.example.qthien.besttrip.view.fragment

import com.example.qthien.besttrip.data.User

interface IFragLogin {
    fun success(user: User?)
    fun failure(message : String)
}