package com.example.qthien.besttrip.model

import android.util.Log
import com.example.qthien.besttrip.data.Result
import com.example.qthien.besttrip.presenter.IPreActiSeDes
import com.example.qthien.besttrip.retrofit.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoActiSeDes(var iPreActiSeDes : IPreActiSeDes) {

    fun getDataBySearchPlace(params : Map<String , String>){
        val retrofitAPI = RetrofitAPI("https://maps.googleapis.com/maps/api/place/nearbysearch/")
        val re = retrofitAPI.getRetrofit
        val call = re.getPlace(params)
        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                iPreActiSeDes.failure("Not find fragment_result for $params['keyword']")
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                Log.d("fragment_result" , response.body().toString())
                val result = response.body() as Result
                if(result.status.equals("OK"))
                    iPreActiSeDes.success(result.results)
                else
                    iPreActiSeDes.failure("Fail")
            }

        })
    }
}