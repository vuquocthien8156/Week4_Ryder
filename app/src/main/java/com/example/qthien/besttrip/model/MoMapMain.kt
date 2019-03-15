package com.example.qthien.besttrip.model

import android.util.Log
import com.example.qthien.besttrip.data.Direction
import com.example.qthien.besttrip.presenter.IPreMapMain
import com.example.qthien.besttrip.retrofit.RetrofitAPI
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoMapMain(var ipreMapMain: IPreMapMain) {
    fun getListStep(params : Map<String , String>){
        val re = RetrofitAPI("https://maps.googleapis.com/maps/api/directions/")
        val reDirection = re.getRetrofit
        val call = reDirection.getDirection(params)
        call.enqueue(object : Callback<Direction>{
            override fun onFailure(call: Call<Direction>, t: Throwable) {
                ipreMapMain.failure("Retrofit Call Steps Fail")
            }

            override fun onResponse(call: Call<Direction>, response: Response<Direction>) {
                val direction : Direction = response.body() as Direction
                Log.d("responseeeee" , response.body().toString())
                Log.d("responseeeee" , direction.toString())
                if(direction.status.equals("OK"))
                {
                    val arr = ArrayList<LatLng>()
                    val arrPolyline = ArrayList<String>()

                    for(step in direction.routes!![0].legs[0].steps){
                        val latLngStart = LatLng(step.start_location!!.lat , step.start_location!!.lng)
                        val latLngEnd = LatLng(step.end_location!!.lat , step.end_location!!.lng)

                        arr.add(latLngStart)
                        arr.add(latLngEnd)
                        arrPolyline.add(step.polyline?.points!!)
                    }

                    ipreMapMain.success(arr , arrPolyline)
                }
                else
                    ipreMapMain.failure("Fail")
            }

        })
    }
}