package com.example.qthien.besttrip.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geometry(
    var location : Location
) : Parcelable