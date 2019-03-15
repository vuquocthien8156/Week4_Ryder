package com.example.qthien.besttrip.view.fragment

import com.example.qthien.besttrip.data.Place

interface IFragSelDes {
    fun success(arrResult : ArrayList<Place>)
    fun failure(message : String)
}