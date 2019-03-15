package com.example.qthien.besttrip.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient



class RetrofitAPI(baseURL : String) {
    private var retrofit : Retrofit? = null
    private val baseUrl : String = baseURL

    val getRetrofit : GetDataAPI
        get(){
            if(retrofit == null){
                val client = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(GetDataAPI::class.java)
        }
}