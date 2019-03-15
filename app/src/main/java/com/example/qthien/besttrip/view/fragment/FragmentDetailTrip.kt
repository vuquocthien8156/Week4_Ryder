package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qthien.besttrip.*
import com.example.qthien.besttrip.data.Leg
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.presenter.PreFragDetailTrip
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.detail_trip.*
import java.text.NumberFormat
import java.util.*

class FragmentDetailTrip : Fragment() , IFragDetailTrip {

    lateinit var iListenerFragmentToMain : IListenerFragmentToMain
    lateinit var taxi : Taxi
    lateinit var info : Leg

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.detail_trip , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain

        taxi = arguments?.getParcelable("taxi")!!

        MainActivity.taxi = taxi

        if(MainActivity.locationDes == null){
            linearTripInfo.visibility = View.GONE
        }
        else{
            val params = mapOf("origin" to "${MainActivity.locationUser?.lat},${MainActivity.locationUser?.lng}" , "destination" to "${MainActivity.locationDes?.lat},${MainActivity.locationDes?.lng}")
            PreFragDetailTrip(this).getInfoTrip(params)
            linearTripInfo.visibility = View.VISIBLE
        }

        txtPrice1.text = taxi.priceFirst
        txtPrice2.text = taxi.priceLast

        GlideApp.with(context).load(taxi.url).into(imgAbout)
    }

    override fun success(info: Leg) {
        this.info = info
        txtKm.text = info.distance?.text
        txtTime.text = info.duration?.text
        val km = info.distance?.text?.replace("k" , "")?.replace("m" , "")
        txtGiaSum.text = pareStringtoPrice(km!!)
    }

    fun pareStringtoPrice(km : String) : String{
        val price = when{
            km.toDouble().compareTo(4f) <= 0 -> taxi.priceFirst!!.toInt().times(km.toDouble())
            else -> (km.toDouble() * taxi.priceFirst!!.toInt()) + ((km.toDouble() - 4f) * taxi.priceLast!!.toInt())
        }

        val localeVN = Locale("vi", "VN")
        val vn = NumberFormat.getInstance(localeVN)
        return (vn.format(price) + "đ")
    }

    override fun failure(message: String) {
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        iListenerFragmentToMain.setVisibilityDrawerButton(false)
        iListenerFragmentToMain.setVisibilityLinearEdt(true)
        iListenerFragmentToMain.setVisibilityIconMap(false)
        iListenerFragmentToMain.setVisibilityIconCall(true)
        iListenerFragmentToMain.setTitleMain(taxi.name!!)
        super.onStart()
    }
}