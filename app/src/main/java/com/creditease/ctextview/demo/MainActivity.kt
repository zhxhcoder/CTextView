package com.creditease.ctextview.demo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ctvTest2.withText("测试一下bold或者normal")
            .withRegex("bold")
            .withColor(Color.BLUE)
            .withSize(20)
            .withBold(true)
            .withBack {

            }


    }
}
