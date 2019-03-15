package com.example.qthien.besttrip.presenter

import com.example.qthien.besttrip.data.Place

interface IPreActiSeDes {
    fun success(arrResult : ArrayList<Place>)
    fun failure(message : String)
}