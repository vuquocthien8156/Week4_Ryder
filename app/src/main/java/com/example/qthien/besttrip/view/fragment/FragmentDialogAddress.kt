package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.qthien.besttrip.R
import kotlinx.android.synthetic.main.dialog_show_address.view.*

class FragmentDialogAddress : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_show_address , container , false)
        view.txtShowAddress.text = arguments?.getString("address")
        return view
    }
}