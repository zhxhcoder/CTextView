package com.creditease.ctextview.demo.test

import java.io.Serializable

/**
 * Created by zhxh on 2019/08/12
 */
data class FortuneCardListInfo(
    val fcardList: List<FortuneCardItemData>?,
    val obtainedAmt: String?,
    val unObtainedAmt: String?
) : Serializable