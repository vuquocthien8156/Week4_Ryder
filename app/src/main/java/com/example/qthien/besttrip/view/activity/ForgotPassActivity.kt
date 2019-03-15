package com.example.qthien.besttrip.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.qthien.besttrip.R

class ForgotPassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.forgotpass)
        setContentView(R.layout.activity_forgot_pass)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
