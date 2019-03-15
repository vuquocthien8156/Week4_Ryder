package com.example.qthien.besttrip.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

class Taxi() : Parcelable{
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(priceFirst)
        dest?.writeString(priceLast)
        dest?.writeString(phone1)
        dest?.writeString(phone2)
        dest?.writeInt(catelogy)
        dest?.writeString(url)
    }

    override fun describeContents(): Int = 0

    var name : String? = null
    var priceFirst : String? = null
    var priceLast: String? = null
    var phone1 : String? = null
    var phone2 : String? = null
    var catelogy : Int = 0
    var url : String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        priceFirst = parcel.readString()
        priceLast = parcel.readString()
        phone1 = parcel.readString()
        phone2 = parcel.readString()
        catelogy = parcel.readInt()
        url = parcel.readString()
    }

    constructor(name : String,
                priceFirst : String,
                priceLast: String,
                phone1 : String,
                phone2 : String,
                catelogy : Int,
                url : String) : this(){
        this.name = name
        this.priceFirst = priceFirst
        this.priceLast = priceLast
        this.phone1 = phone1
        this.phone2 = phone2
        this.catelogy = catelogy
        this.url = url
    }

    companion object CREATOR : Parcelable.Creator<Taxi> {
        override fun createFromParcel(parcel: Parcel): Taxi {
            return Taxi(parcel)
        }

        override fun newArray(size: Int): Array<Taxi?> {
            return arrayOfNulls(size)
        }
    }
}