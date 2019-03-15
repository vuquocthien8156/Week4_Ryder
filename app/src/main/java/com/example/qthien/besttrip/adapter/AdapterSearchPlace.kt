package com.example.qthien.besttrip.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.MainActivity
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.data.Location
import com.example.qthien.besttrip.data.Place
import com.example.qthien.besttrip.view.fragment.FragmentResult
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.item_lv_place.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class AdapterSearchPlace(
    var con: Context,
    var arr: ArrayList<Place>,
    var fragmentManager: FragmentManager,
    var call: String,
    var iListenerFragmentToMain: IListenerFragmentToMain? = null
) :
    BaseAdapter() {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return arr[position]
    }

    override fun getCount(): Int {
        return arr.size
    }

    class ViewHolder{
        lateinit var txtName : TextView
        lateinit var txtSoKm : TextView
    }

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder : ViewHolder

        viewHolder = ViewHolder()
        val view = LayoutInflater.from(con).inflate(R.layout.item_lv_place , parent , false)
        viewHolder.txtName = view.txtNamePlace
        viewHolder.txtSoKm = view.txtSumKm

        viewHolder.txtName.setText(arr[position].name)

        // Caculator km
        val startLatLng = LatLng(MainActivity.locationUser?.lat!! , MainActivity.locationUser?.lng!!)
        val endLatLng = LatLng(arr[position].geometry.location.lat, arr[position].geometry.location.lng)
        val distance : Double = SphericalUtil.computeDistanceBetween(startLatLng, endLatLng)

        val number3digits:Double = Math.round((distance/1000) * 1000.0) / 1000.0
        val number2digits:Double = Math.round(number3digits * 100.0) / 100.0
        val km:Double = Math.round(number2digits * 10.0) / 10.0
        viewHolder.txtSoKm.text = km.toString() + "km"

        view.setOnClickListener({
            if(call.equals("frag_main")){
                MainActivity.locationDes = Location(arr[position].geometry.location.lat , arr[position].geometry.location.lng)
                Log.d("TOADO" , "${arr[position].geometry.location.lat} , ${arr[position].geometry.location.lng}")
                val fragmentResult = FragmentResult()
                val bundle = Bundle()
                bundle.putParcelable("place" , arr[position])
                bundle.putString("km" , km.toString() )
                fragmentResult.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.frmMain2 , fragmentResult).addToBackStack("fragment_result").commit()
            }
            else{
                MainActivity.locationDes = Location(arr[position].geometry.location.lat , arr[position].geometry.location.lng)
                iListenerFragmentToMain?.setVisibilityLinearEdt(true , arr[position].name)
                fragmentManager.popBackStack()
            }
            ((con as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken , 0)
        })
        return view!!
    }
}