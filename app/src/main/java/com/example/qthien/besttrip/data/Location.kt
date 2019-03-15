package com.example.qthien.besttrip.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (
    var lat : Double,
    var lng : Double
) : Parcelable