package com.example.qthien.besttrip.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaxByCatelogy (
    var nameCate : String,
    var arrTaxi : ArrayList<Taxi>,
    var sizeAll : Int
) : Parcelable