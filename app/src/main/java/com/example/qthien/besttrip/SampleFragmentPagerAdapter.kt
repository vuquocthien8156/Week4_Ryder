package com.example.qthien.besttrip

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.qthien.besttrip.view.fragment.FragmentLogin
import com.example.qthien.besttrip.view.fragment.FragmentRegister

class SampleFragmentPagerAdapter(var fm: FragmentManager?, var context: FragmentActivity?) : FragmentPagerAdapter(fm){
    val PAGE_COUNT = 2

    private val tabTitles = arrayOf("Login", "Register")

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return FragmentLogin.newInstance()
            1 -> return FragmentRegister.newInstance()
        }
        return FragmentLogin()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return tabTitles[position]
    }
}