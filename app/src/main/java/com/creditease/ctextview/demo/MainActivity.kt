package com.creditease.ctextview.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.creditease.ctextview.demo.test.BaseFinanceNetResp
import com.creditease.ctextview.demo.test.FortuneCardListInfo
import com.creditease.ctextview.demo.test.YiriCardData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.JsonParser
import com.google.gson.JsonObject
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.creditease.ctextview.demo.test.DataMap


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultStr =
            """["1111","2222","3333"]"""

        val resultStrMap =
            """{"data":{"帮我分析":[{"answerType":1005,"name":"专属人工管家11111112","pic":"图片地址"}],"帮我理财":[{"answerType":1005,"name":"专属人2","pic":"图片地址"}],"帮我选品":[{"answerType":1005,"name":"专属人工管家12","pic":"图片地址"}]},"errorCode":"","msg":"","result":"success"}"""
        val mGson = Gson()
        val type0 = object : TypeToken<List<YiriCardData>>() {}.type
        val type1 = object : TypeToken<BaseFinanceNetResp<YiriCardData>>() {}.type
        val typeMap = object : TypeToken<BaseFinanceNetResp<Map<String,List<YiriCardData>>>>() {}.type


        //val strList1 =
        //    Regex("""(?<=帮我分析.{2})\[[^\]]+\]""").find(resultStr)?.groupValues?.get(0) ?: ""
        //val tags1: List<YiriCardData> = mGson.fromJson<List<YiriCardData>>(strList1, type0)

        val jsonObject = JsonParser().parse(resultStr).asJsonObject
        val jsonData =jsonObject.getAsJsonObject("这个是啥")

        val respMap = Gson().fromJson<BaseFinanceNetResp<Map<String,List<YiriCardData>>>?>(resultStrMap, typeMap)


        tstTxt.text = respMap!!.data.get("帮我理财")?.get(0)?.name ?: ""


        ctvTest.setOnClickListener {
            Log.d("XXXXX", "时间-" + System.currentTimeMillis())
        }


        val s =
            "{\"data\":{\"fcardList\":[{\"balance\":222222,\"businessType\":1,\"buyMaxAmt\":100000,\"cardName\":\"福卡名称\",\"cardType\":10,\"description\":\"这是福卡描述\",\"endTime\":1563701297000,\"endTimeStr\":\"2019.08.12\",\"id\":4,\"increaseInterestDay\":180,\"increaseInterestDurationText\":\"30天额外收益\",\"increaseInterestRate\":\"1.0表示加息1%\",\"increaseInterestRateText\":\"+3.60%\",\"quotaIdList\":{\"1\":\"first\",\"2\":null,\"3\":\"third\"},\"quotaIds\":41,\"startTime\":1563701257000,\"startTimeStr\":\"2019.08.12\",\"status\":2,\"systemTime\":1563701297000}],\"obtainedAmt\":100,\"unObtainedAmt\":20000},\"errorCode\":3004,\"msg\":\"加载失败，请稍后再试\",\"result\":\"success\"}"

        val type = object : TypeToken<BaseFinanceNetResp<FortuneCardListInfo>?>() {
        }.type
        val resp = Gson().fromJson<BaseFinanceNetResp<FortuneCardListInfo>?>(s, type)


        ctvTest.text = resp?.data?.fcardList?.get(0)?.quotaIdList!!["3"]

    }
}
