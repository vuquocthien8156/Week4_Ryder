package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qthien.besttrip.IListenerFragViewPagerCallParent
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.SampleFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.fragment_login_register.view.*

class FragmentLogin_Register : Fragment() , IListenerFragViewPagerCallParent {

    lateinit var iListenerFragmentToMain : IListenerFragmentToMain

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_login_register , container , false)
        view.viewpagerLogin.adapter = SampleFragmentPagerAdapter(
            childFragmentManager ,
            activity)
        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain
        view.tabLayout.setupWithViewPager(view.viewpagerLogin)

        return view
    }

    override fun setSelectedPageLogin() {
        view?.viewpagerLogin?.currentItem = 0
    }

    override fun onStart() {
        super.onStart()
        iListenerFragmentToMain.setVisibilityLinearEdt(false)
        iListenerFragmentToMain.setVisibilityDrawerButton(true)
        iListenerFragmentToMain.setTitleMain()
        iListenerFragmentToMain.setVisibilityIconCall(false)
        iListenerFragmentToMain.setVisibilityIconMap(false)
    }
}