package com.example.qthien.besttrip.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place (
    var geometry : Geometry,
    var name : String,
    var id : String
) : Parcelable