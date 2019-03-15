package com.example.qthien.besttrip.retrofit

import com.example.qthien.besttrip.data.Direction
import retrofit2.Call
import com.example.qthien.besttrip.data.Result
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GetDataAPI {
    @GET("json?key=AIzaSyCe39vZ4iTF1nrvpmo6g1hQwsw9sa2G6to")
    fun getPlace(@QueryMap Params : Map<String , String>) : Call<Result>

    @GET("json?key=AIzaSyDKdD855kivloeO1yk-ok4UeUgb3_OuIx6U")
    fun getDirection(@QueryMap Params : Map<String , String>) : Call<Direction>
}