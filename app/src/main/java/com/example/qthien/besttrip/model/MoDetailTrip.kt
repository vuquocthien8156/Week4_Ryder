package com.example.qthien.besttrip.model

import android.util.Log
import com.example.qthien.besttrip.data.Direction
import com.example.qthien.besttrip.presenter.IPreFragDeatilTrip
import com.example.qthien.besttrip.retrofit.RetrofitAPI
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoDetailTrip(var iPreFragDeatilTrip: IPreFragDeatilTrip) {

    fun getInfo(params : Map<String , String>){
        val re = RetrofitAPI("https://maps.googleapis.com/maps/api/directions/")
        val reDirection = re.getRetrofit
        val call = reDirection.getDirection(params)
        call.enqueue(object : Callback<Direction> {
            override fun onFailure(call: Call<Direction>, t: Throwable) {
                iPreFragDeatilTrip.failure("Retrofit Call Steps Fail")
            }

            override fun onResponse(call: Call<Direction>, response: Response<Direction>) {
                val direction : Direction = response.body() as Direction
                Log.d("responseeeee" , response.body().toString())
                Log.d("responseeeee" , direction.toString())
                if(direction.status.equals("OK"))
                {
                    if(direction.routes?.size != null)
                        iPreFragDeatilTrip.success(direction.routes!![0].legs[0])
                    else
                        iPreFragDeatilTrip.failure("Please again")
                }
                else
                    iPreFragDeatilTrip.failure("Fail")
            }

        })
    }
}