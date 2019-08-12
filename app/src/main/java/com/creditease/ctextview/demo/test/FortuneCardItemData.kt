package com.creditease.ctextview.demo.test

import java.io.Serializable

/**
 * Created by zhxh on 2019/08/12
 */
data class FortuneCardItemData(
    /**
     * "balance":222222,
    "businessType":1,
    "buyMaxAmt":100000,
    "cardName":"福卡名称",
    "cardType":10,
    "description":"这是福卡描述",
    "endTime":1563701297000,
    "endTimeStr":"2019.08.12",
    "id":4,
    "increaseInterestDay":180,
    "increaseInterestRate":"1.0表示加息1%",
    "quotaIdList":{
    "1":null,
    "2":null,
    "3":null
    },
    "quotaIds":41,
    "startTime":1563701257000,
    "startTimeStr":"2019.08.12",
    "status":2
     */

    var balance: String = "",
    var businessType: String = "",
    var buyMaxAmt: String = "",
    var cardType: String = "",
    var description: String = "",
    var endTime: String = "",
    var endTimeStr: String = "",
    var id: String = "",
    var increaseInterestDay: String = "",
    var increaseInterestRate: String = "",
    var startTime: String = "",
    var startTimeStr: String = "",
    var status: String = "",
    var quotaIds: String = "",
    var cardName: String = "",
    var quotaIdList: Map<String, String>
) : Serializable