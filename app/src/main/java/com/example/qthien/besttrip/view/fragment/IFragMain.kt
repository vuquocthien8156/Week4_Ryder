package com.example.qthien.besttrip.view.fragment

interface IFragMain {
    fun success(result: String , latitude : Double , longitude : Double)
    fun failure(message: String)
}