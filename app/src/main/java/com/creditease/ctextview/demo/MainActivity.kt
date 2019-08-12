package com.creditease.ctextview.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.creditease.ctextview.demo.test.BaseFinanceNetResp
import com.creditease.ctextview.demo.test.FortuneCardListInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctvTest.setOnClickListener {
            Log.d("XXXXX", "时间-" + System.currentTimeMillis())
        }



        val s="{\"data\":{\"fcardList\":[{\"balance\":222222,\"businessType\":1,\"buyMaxAmt\":100000,\"cardName\":\"福卡名称\",\"cardType\":10,\"description\":\"这是福卡描述\",\"endTime\":1563701297000,\"endTimeStr\":\"2019.08.12\",\"id\":4,\"increaseInterestDay\":180,\"increaseInterestDurationText\":\"30天额外收益\",\"increaseInterestRate\":\"1.0表示加息1%\",\"increaseInterestRateText\":\"+3.60%\",\"quotaIdList\":{\"1\":\"first\",\"2\":null,\"3\":\"third\"},\"quotaIds\":41,\"startTime\":1563701257000,\"startTimeStr\":\"2019.08.12\",\"status\":2,\"systemTime\":1563701297000}],\"obtainedAmt\":100,\"unObtainedAmt\":20000},\"errorCode\":3004,\"msg\":\"加载失败，请稍后再试\",\"result\":\"success\"}"

        val type = object : TypeToken<BaseFinanceNetResp<FortuneCardListInfo>?>() {
        }.type
        val resp = Gson().fromJson<BaseFinanceNetResp<FortuneCardListInfo>?>(s, type)


        ctvTest.text= resp?.data?.fcardList?.get(0)?.quotaIdList!!["3"]

    }
}
