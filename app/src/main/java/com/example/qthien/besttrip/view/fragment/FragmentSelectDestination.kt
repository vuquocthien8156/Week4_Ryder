package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.MainActivity
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.adapter.AdapterSearchPlace
import com.example.qthien.besttrip.data.Place
import com.example.qthien.besttrip.presenter.PreActiSeDes
import kotlinx.android.synthetic.main.fragment_select_destination.view.*

class FragmentSelectDestination : Fragment() ,
    IFragSelDes {

    private val KEYWORD = "keyword"
    private val LOCATION = "location"

    lateinit var arrPlace : ArrayList<Place>
    lateinit var adapter : AdapterSearchPlace
    var preActiSeDes : PreActiSeDes

    init {
        preActiSeDes = PreActiSeDes(this)
    }

    lateinit var iListenerFragmentToMain : IListenerFragmentToMain

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_select_destination , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain

        arrPlace = ArrayList()

        val call = arguments?.getString("call")!!
        adapter = AdapterSearchPlace(context!! , arrPlace , activity!!.supportFragmentManager, call , iListenerFragmentToMain)
        view.lvPlace.adapter = adapter

//        edtSearchPlace.addTextChangedListener(object : TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if(s.isNullOrEmpty())
//                {
//                    arrPlace.clear()
//                    adapter.notifyDataSetChanged()
//                }
//                else{
//                    val location = "${MainActivity.locationUser?.lat},${MainActivity.locationUser?.lng}"
//                    val params = mapOf(LOCATION to location ,KEYWORD to s.toString(),"radius" to "1500")
//                    preActiSeDes.getDataBySearchPlace(params)
//                }
//
//            }
//
//        })
        view.edtSearchPlace.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (view.edtSearchPlace.text.isNullOrEmpty()) {
                        arrPlace.clear()
                        adapter.notifyDataSetChanged()
                    } else {
                        val location = "${MainActivity.locationUser?.lat},${MainActivity.locationUser?.lng}"
                        val params =
                            mapOf(LOCATION to location, KEYWORD to view.edtSearchPlace.text.toString(), "radius" to "1500")
                        preActiSeDes.getDataBySearchPlace(params)
                    }
                    Log.d("Clear" , "aaaaaa")
                    view.progressBarSearchPlace.visibility = View.VISIBLE

                    return true
                }
                return false
            }
        })
    }

    override fun onStart() {
        iListenerFragmentToMain.setVisibilityDrawerButton(false)
        iListenerFragmentToMain.setTitleMain("Select Destination")
        iListenerFragmentToMain.setVisibilityLinearEdt(false)
        iListenerFragmentToMain.setVisibilityIconMap(false)
        iListenerFragmentToMain.setVisibilityIconCall(false)
        super.onStart()
    }

    override fun success(arrResult: ArrayList<Place>) {
        view?.progressBarSearchPlace?.visibility = View.GONE
        arrPlace.clear()
        arrPlace.addAll(arrResult)
        adapter.notifyDataSetChanged()
    }

    override fun failure(message: String) {
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
    }
}
