package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.adapter.AdapterSearchTaxi
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.presenter.PreFragResult
import kotlinx.android.synthetic.main.fragment_search_taxi.*
import kotlinx.android.synthetic.main.fragment_search_taxi.view.*

class FragmentSearchTaxi : Fragment() , IFragResult{

    lateinit var arrTaxi : ArrayList<Taxi>
    lateinit var arrTaxi2 : ArrayList<Taxi>
    lateinit var adapter : AdapterSearchTaxi
    lateinit var iListenerFragmentToMain : IListenerFragmentToMain

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_search_taxi , container , false)
        arrTaxi = ArrayList()
        arrTaxi2 = ArrayList()
        adapter = AdapterSearchTaxi(context!! , arrTaxi)
        view.recylerTaxi.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        view.recylerTaxi.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain

        PreFragResult(this).getAllData()

        ibtnSearch.setOnClickListener({
            filterTaxi()
            Log.d("Loggg" , "aaaaaaa")
        })
    }

    override fun onStart() {
        iListenerFragmentToMain.setTitleMain("Search Taxi")
        iListenerFragmentToMain.setVisibilityLinearEdt(false)
        iListenerFragmentToMain.setCheckedNavDrawer(R.id.nav_charges)
        super.onStart()
    }

    private fun filterTaxi() {
        val arrResult = ArrayList<Taxi>()
        val query = edtSearchTaxi.text.toString()
        if(!query.equals("")){
            for (taxi in arrTaxi2)
            {
                if(taxi.name!!.toLowerCase().contains(query.toLowerCase())){
                    arrResult.add(taxi)
                }
            }

            if(arrResult.isEmpty()){
                txtNotification.setText("No fragment_result for '$query'")
                txtNotification.visibility = View.GONE
                arrTaxi.clear()
                adapter.notifyDataSetChanged()
            }
            else{
                arrTaxi.clear()
                arrTaxi.addAll(arrResult)
                adapter.notifyDataSetChanged()
            }
        }
        else{
            arrTaxi.clear()
            adapter.notifyDataSetChanged()
        }
    }

    override fun success(listTaxi: List<Taxi>) {
//        arrTaxi.addAll(listTaxi)
        arrTaxi2.addAll(listTaxi)
    }

    override fun failure(message: String) {
        Toast.makeText(context , "Fail load data" , Toast.LENGTH_SHORT).show()
    }
}