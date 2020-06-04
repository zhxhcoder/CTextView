package com.creditease.ctextview.demo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.creditease.ctextview.demo.pay.demo.PayDemoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctvTest2.withText("测试一下RxJava搜索")
            .withRegex("RxJava")
            .withColor(Color.BLUE)
            .withSize(20)
            .withBold(true)
            .withBack {
                startActivity(Intent(this,RxjavaActivity::class.java))
                startActivity(Intent(this,PayDemoActivity::class.java))
            }

    }
}
