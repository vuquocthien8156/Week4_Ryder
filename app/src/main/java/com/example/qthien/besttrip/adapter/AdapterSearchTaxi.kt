package com.example.qthien.besttrip.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Filter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.qthien.besttrip.GlideApp
import com.example.qthien.besttrip.MainActivity
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.view.fragment.FragmentDetailTrip
import kotlinx.android.synthetic.main.item_recycler_taxi_search.view.*

class AdapterSearchTaxi(var con : Context , var arrTaxi : ArrayList<Taxi>) : RecyclerView.Adapter<AdapterSearchTaxi.ViewHolder>()  {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(con).inflate(R.layout.item_recycler_taxi_search , p0 , false))
    }

    override fun getItemCount(): Int {
        return arrTaxi.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val taxi = arrTaxi[p1]
        p0.txtName.text = taxi.name
        val phone = when{
            !taxi.phone2.equals("") -> "${taxi.phone1} / ${taxi.phone2}"
            else -> taxi.phone1
        }
        p0.txtPhone.text = phone

        GlideApp.with(con).load(taxi.url).into(p0.img)

        p0.layout.setOnClickListener({
            val fragmentDetailTrip = FragmentDetailTrip()
            val bundle = Bundle()
            bundle.putParcelable("taxi" , taxi)
            fragmentDetailTrip.arguments = bundle
            ((con as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(it.windowToken , 0)
            (con as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.frmMain2 , fragmentDetailTrip).addToBackStack("detail_trip").commit()
        })
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var txtName : TextView = itemView.txtNameTaxiSearch
        var txtPhone : TextView = itemView.txtSdtTaxiSearch
        var img : ImageView = itemView.imgTaxi
        var layout : RelativeLayout = itemView.layoutItemSearch
    }
}