package com.example.qthien.besttrip.view.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.presenter.PreFragMain
import android.widget.Toast

import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.MainActivity
import com.example.qthien.besttrip.data.Location
import com.example.qthien.besttrip.data.Place

import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*


class FragmentMain() : Fragment(), IFragMain, ActivityCompat.OnRequestPermissionsResultCallback {

    lateinit var preFragMain : PreFragMain
//    val Request_Code_SelectDes = 101
    lateinit var iListenerFragmentToMain : IListenerFragmentToMain
    var address : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        preFragMain = PreFragMain(context , this)

        MainActivity.locationDes = null

        for(i in activity!!.supportFragmentManager.fragments){
            if(i.tag != "main")
                activity!!.supportFragmentManager.beginTransaction().remove(i).commit()
        }

        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain

        if(ActivityCompat.checkSelfPermission(context!! , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(activity!! , arrayOf(Manifest.permission.ACCESS_FINE_LOCATION) , 100)
        else {
            Log.d("TAGGG" , "Yes")
            preFragMain.getLocation()
        }
        return LayoutInflater.from(context).inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken , 0)
        view.btnWhere.setOnClickListener({
            if(MainActivity.locationUser != null){
                val fragmentSelectDestination = FragmentSelectDestination()
                val bundle = Bundle()
                bundle.putString("call" , "frag_main")
                fragmentSelectDestination.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frmMain2 , fragmentSelectDestination)
                    ?.addToBackStack("select_des")?.commit()
            }
            else
                Toast.makeText(context , "Chưa lấy được vị trí hiện tại" , Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        iListenerFragmentToMain.setVisibilityDrawerButton(true)
        iListenerFragmentToMain.setTitleMain()
        iListenerFragmentToMain.setVisibilityIconCall(false)
        iListenerFragmentToMain.setVisibilityIconMap(false)
        iListenerFragmentToMain.setVisibilityLinearEdt(false)
        iListenerFragmentToMain.setCheckedNavDrawer(R.id.nav_home)
        super.onStart()
    }

    // Call back get Location .... PreFraMain
    override fun success(result: String, latitude: Double, longitude: Double) {
        txtLocation.setText(result)
        address = result
        MainActivity.locationUser = Location(latitude, longitude)
        Log.d("TOADO" , "${latitude} - ${longitude}" )
        Log.d("TOADO" , "${MainActivity.locationUser?.lat} --- ${MainActivity.locationUser?.lng}" )
    }

    override fun failure(message: String) {
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                // If request is cancelled, the fragment_result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    preFragMain.getLocation()
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}