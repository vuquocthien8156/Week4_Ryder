package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.adapter.AdapterTaxi
import com.example.qthien.besttrip.data.Taxi
import kotlinx.android.synthetic.main.fragment_all_taxibycate.*

class FragmentAllTaxiByCate : Fragment() {

    lateinit var iListenerFragmentToMain : IListenerFragmentToMain
    lateinit var arrTaxi : ArrayList<Taxi>
    lateinit var adapter : AdapterTaxi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_all_taxibycate , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain

        arrTaxi = ArrayList()
        arrTaxi.addAll(arguments!!.getParcelableArrayList("listTaxi")!!)

        adapter = AdapterTaxi(context!! , arrTaxi , arguments!!.getString("km")!! , onClickStartFragment)
        recylerTaxi.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        recylerTaxi.adapter = adapter
    }

    var onClickStartFragment = fun(fragment: Fragment , tag : String){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.frmMain2 , fragment , tag).addToBackStack(tag).commit()
    }

    override fun onStart() {
        iListenerFragmentToMain.setVisibilityDrawerButton(false)
        iListenerFragmentToMain.setTitleMain(arguments?.getString("nameCate")!!)
        iListenerFragmentToMain.setVisibilityIconCall(false)
        iListenerFragmentToMain.setVisibilityIconMap(true)
        super.onStart()
    }
}