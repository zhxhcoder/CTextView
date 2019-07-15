package com.creditease.ctextview.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctvTest.setOnClickListener {
            Log.d("XXXXX", "时间-" + System.currentTimeMillis())
        }
    }
}
