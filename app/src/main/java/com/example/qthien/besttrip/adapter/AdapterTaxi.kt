package com.example.qthien.besttrip.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.view.fragment.FragmentDetailTrip
import kotlinx.android.synthetic.main.item_lv_taxi.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class AdapterTaxi(var con : Context ,
                  var arrTaxi : List<Taxi> ,
                  var km : String ,
                  val startFragment : (fragment: Fragment, tag : String) -> Unit) : RecyclerView.Adapter<AdapterTaxi.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(con).inflate(R.layout.item_lv_taxi , p0 , false))
    }

    override fun getItemCount(): Int = arrTaxi.size

    override fun onBindViewHolder(viewHolder: ViewHolder, p1: Int) {

        val item = arrTaxi[p1]
        viewHolder.txtName.text = item.name

        val phone = when{
            !item.phone2.equals("") -> "- ${item.phone1} - ${item.phone2}"
            else -> item.phone1
        }
        viewHolder.txtPhone.text = phone

        val price = when{
            km.toDouble().compareTo(4f) <= 0 -> item.priceFirst!!.toInt().times(km.toDouble())
            else -> (km.toDouble() * item.priceFirst!!.toInt()) + ((km.toDouble() - 4f) * item.priceLast!!.toInt())
        }

        val localeVN = Locale("vi", "VN")
        val vn = NumberFormat.getInstance(localeVN)
        val priceString = "${vn.format(price)}đ"
        viewHolder.txtGia.text = priceString

        viewHolder.linearTaxi.setOnClickListener({
            val fragmentDetailTrip = FragmentDetailTrip()
            val bundle = Bundle()
            bundle.putParcelable("taxi" , item)
            fragmentDetailTrip.arguments = bundle
            startFragment(fragmentDetailTrip , "detail_trip")
        })
    }

    inner class ViewHolder(var view : View) : RecyclerView.ViewHolder(view){
        var txtName = view.txtNameTaxi
        var txtPhone = view.txtSdtTaxi
        var txtGia = view.txtGiaTaxi
        var linearTaxi = view.linearTaxi
    }
}