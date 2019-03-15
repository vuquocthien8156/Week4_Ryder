package com.example.qthien.besttrip.view.fragment

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qthien.besttrip.IListenerFragmentToMain
import com.example.qthien.besttrip.MainActivity
import com.example.qthien.besttrip.R
import com.example.qthien.besttrip.data.User
import com.example.qthien.besttrip.presenter.PreFragLogin
import com.example.qthien.besttrip.view.activity.ForgotPassActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.abc_alert_dialog_material.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FragmentLogin : Fragment() , IFragLogin {

    companion object {
        fun newInstance(): FragmentLogin {
            val args = Bundle()
            val fragment = FragmentLogin()
            fragment.setArguments(args)
            return fragment
        }
    }
    lateinit var iListenerFragmentToMain : IListenerFragmentToMain
    lateinit var mAuth : FirebaseAuth
    val bus = EventBus.getDefault()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bus.register(this)
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(user : User){
        edtEmailLogin.setText(user.email)
        edtPasswordLogin.setText(user.password)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAuth = FirebaseAuth.getInstance()
        return LayoutInflater.from(context).inflate(R.layout.fragment_login , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(context is IListenerFragmentToMain)
            iListenerFragmentToMain = context as IListenerFragmentToMain

        view.btnLogin.setOnClickListener({
            val email = edtEmailLogin.text.toString()
            val pass = edtPasswordLogin.text.toString()
            if(email.isEmpty() || pass.isEmpty()){

            }
            else{
                view.progressBarLogin.visibility = View.VISIBLE
                PreFragLogin(this).login(mAuth , activity!! , email , pass)
            }
        })

        view.txtForgotPass.setOnClickListener({
            startActivity(Intent(activity , ForgotPassActivity::class.java))
        })
    }

    override fun failure(message: String) {
        Log.d("ErrorLogin" , message)
    }

    override fun success(user: User?) {
        Log.d("SuccessLogin" , user.toString())
        if(user != null)
        {
            val sharedPreferences = activity?.getSharedPreferences("Login" , Activity.MODE_PRIVATE)
            val editor = sharedPreferences!!.edit()
            editor.putString("id" , user.id).apply()

            iListenerFragmentToMain.setLoginSuccess(user)
            view?.progressBarLogin?.visibility = View.GONE
        }
    }
}