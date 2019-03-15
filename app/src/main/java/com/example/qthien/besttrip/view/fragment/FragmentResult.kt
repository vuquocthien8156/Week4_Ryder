package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.adapter.AdapterResult
import com.example.qthien.besttrip.data.Place
import com.example.qthien.besttrip.data.TaxByCatelogy
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.presenter.PreFragResult
import kotlinx.android.synthetic.main.fragment_result.*

class FragmentResult : Fragment(), IFragResult{

    lateinit var iListenerFragmentToMain : IListenerFragmentToMain
    lateinit var placeSelected : Place
    lateinit var mapTaxi : HashMap<String , List<Taxi>>
    lateinit var adapter : AdapterResult

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_result , container ,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain

        //get place from Selected Place Fragment
        placeSelected = arguments?.getParcelable("place") as Place
        val km = arguments?.getString("km")

        mapTaxi = HashMap()

        adapter = AdapterResult(context!! , mapTaxi , km!! , onClickStartFragment)
        recylerResult.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        recylerResult.adapter = adapter

        PreFragResult(this).getAllData()
    }

    var onClickStartFragment = fun(fragment: Fragment , tag : String){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.frmMain2 , fragment , tag).addToBackStack(tag).commit()
    }

    fun getMapTaxiFolowCate(listTaxi: List<Taxi>) : Map<String , List<Taxi>>{
        val listTaxiByCate = mutableMapOf<String , List<Taxi>>()
        val arrTaxiCatelogy = mapOf(
            1 to "4 Seaters Economy Taxi",
            2 to "7 Seaters Economy Taxi",
            3 to "Premium Taxi's")
        for ((id , name) in arrTaxiCatelogy){
            val listFilter = listTaxi.filter { it.catelogy == id }
            listFilter.sortedByDescending { it.priceLast + it.priceFirst }

            listTaxiByCate.put(name , listFilter)
        }
        return listTaxiByCate
    }

    override fun success(listTaxi: List<Taxi>) {
        mapTaxi.putAll(getMapTaxiFolowCate(listTaxi))
        adapter.notifyDataSetChanged()
    }

    override fun failure(message: String) {
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        iListenerFragmentToMain.setVisibilityDrawerButton(true)
        iListenerFragmentToMain.setVisibilityIconMap(true)
        iListenerFragmentToMain.setVisibilityIconCall(false)
        iListenerFragmentToMain.setTitleMain("Resutls")
        iListenerFragmentToMain.setVisibilityLinearEdt(true, placeSelected.name)
        super.onStart()
    }
}