package com.example.qthien.besttrip.presenter

interface IPreFragMain {
    fun success(result: String, latitude : Double , longitude : Double)
    fun failure(message: String)
}