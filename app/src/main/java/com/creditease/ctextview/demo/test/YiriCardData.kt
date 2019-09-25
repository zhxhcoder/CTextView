package com.creditease.ctextview.demo.test

import java.io.Serializable

/**
 * Created by zhxh on 2019/09/23
 * 包含所有卡片的信息
 */
data class YiriCardData(
    //待办事项单项返回
    var buttonName: String = "",
    var cardContent: String = "",
    var icon: String = "",
    var title: String = "",
    var url: String = "",

    //待办事项多项返回
    var redirectUrl: String = "",

    //基金组合
    var dailyRiseFall: String = "",
    var fiveYearRiseFall: String = "",
    var groupId: String = "",
    var groupName: String = "",

    //银行理财
    var bankName: String = "",
    var days: String = "",
    var enBankName: String = "",
    var incomeMultiple: String = "",
    var incomeTypeName: String = "",
    var minStartAmount: String = "",
    var productName: String = "",

    //每日估值表
    var indexName: String = "",
    var pb: String = "",
    var pe: String = "",
    var ps: String = "",
    var roe: String = "",

    //客服热门回答 猜你想问
    var questions: ArrayList<String>,

    //百度百科 | 学点理财知识
    var href: String = "",
    var text: String = "",


    //宜人动态 | 理财圈文章
    var weak: String = "",
    var cnTimes: String = "",
    var dateString: String = "",
    var articleId: String = "",
    var subjectName: String = "",
    var reports: ArrayList<YiriCardData>,

    //名词解释
    var showResponse: String = "",

    //人工客服
    var whatCanIDo: String = "",
    var telephone: String = "",
    var email: String = "",
    var name: String = "",
    var address: String = "",
    var company: String = "",

    ///support/yiri/items
    var answerType: String = "",
    var pic: String = "",
    var redDot: String = "",





    var data: ArrayList<YiriCardData>

) : Serializable {
    val textList: List<String>
        get() {
            return if (text.isEmpty()) java.util.ArrayList() else text.split("，".toRegex()).dropLastWhile { it.isEmpty() }
        }

}

