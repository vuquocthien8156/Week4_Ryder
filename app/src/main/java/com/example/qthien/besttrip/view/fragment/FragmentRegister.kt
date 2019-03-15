package com.example.qthien.besttrip.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qthien.besttrip.IListenerFragViewPagerCallParent
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.data.User
import com.example.qthien.besttrip.presenter.PreFragRegister
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.greenrobot.eventbus.EventBus

class FragmentRegister : Fragment(), IFragRegister {

    companion object {
        fun newInstance(): FragmentRegister {
            val args = Bundle()

            val fragment = FragmentRegister()
            fragment.setArguments(args)
            return fragment
        }
    }

    var userRegister : User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_register , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegistEmail.setOnClickListener({
            if(edtUsername.text.toString().isEmpty() ||  edtEmailRegister.text.toString().isEmpty() ||  edtPasswordRegister.text.toString().isEmpty())

            else{
                view.progressBarRegister.visibility = View.VISIBLE
                userRegister = User(edtUsername.text.toString() , edtEmailRegister.text.toString() , edtPasswordRegister.text.toString() )
                PreFragRegister(this).register(userRegister!!, activity!!)
            }
        })
    }

    override fun success(message: String) {
        Log.d("successRegister" , message)
        if(message.equals("success"))
        {
            view?.progressBarRegister?.visibility = View.GONE
            val iListenerFragViewPagerCallParent : IListenerFragViewPagerCallParent
            if(parentFragment is IListenerFragViewPagerCallParent){
                iListenerFragViewPagerCallParent = parentFragment as IListenerFragViewPagerCallParent
                iListenerFragViewPagerCallParent.setSelectedPageLogin()
            }
            val eventbus = EventBus.getDefault()
            eventbus.post(userRegister)
            Toast.makeText(context , "Register successfull" , Toast.LENGTH_SHORT).show()
            edtEmailRegister.text.clear()
            edtPasswordRegister.text.clear()
            edtUsername.text.clear()
        }
    }

    override fun failure(message: String) {
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
    }
}