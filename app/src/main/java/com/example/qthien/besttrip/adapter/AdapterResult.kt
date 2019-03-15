package com.example.qthien.besttrip.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.view.fragment.FragmentAllTaxiByCate
import kotlinx.android.synthetic.main.item_recycler_result.view.*

class AdapterResult(
    var con: Context,
    var map: Map<String , List<Taxi>>, var km: String,val startFragment : (fragment: Fragment, tag : String) -> Unit) :
    RecyclerView.Adapter<AdapterResult.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(con).inflate(R.layout.item_recycler_result , p0 , false))
    }

    override fun getItemCount(): Int = map.size

    override fun onBindViewHolder(vh : ViewHolder, po : Int) {
        vh.setIsRecyclable(false)
        val keys = ArrayList(map.keys)
        val values = ArrayList(map.values)
        vh.txtNameCatelory.text = keys[po]

        val count = "View all ${values[po].size}"
        vh.txtCountAll.text = count

        val arrFilter = mutableListOf(values[po].first() , values[po].last())
        val adapterTaxi = AdapterTaxi(con , arrFilter , km , startFragment)
        vh.recyclerTaxi.layoutManager = LinearLayoutManager(con , LinearLayoutManager.VERTICAL , false)
        vh.recyclerTaxi.adapter = adapterTaxi

        vh.ibtnToAll.setOnClickListener({
            val fragmentAllTaxiByCate = FragmentAllTaxiByCate()
            val bundle = Bundle()
            bundle.putString("km" , km)
            bundle.putString("nameCate" , keys[po])
            val arr = ArrayList<Taxi>()
            arr.addAll(values[po])
            bundle.putParcelableArrayList("listTaxi" , arr)
            fragmentAllTaxiByCate.arguments = bundle
            startFragment(fragmentAllTaxiByCate , "to_all")
        })
    }


    inner class ViewHolder(viewItem : View) : RecyclerView.ViewHolder(viewItem){
        var txtNameCatelory : TextView = viewItem.txtNameResult
        var recyclerTaxi : RecyclerView = viewItem.recyclerTaxi
        var txtCountAll : TextView = viewItem.txtCount
        var ibtnToAll : ImageButton = viewItem.ibtnToAll
    }
}