package com.creditease.ctextview.demo.test;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by zhxh on 2019/09/27
 */
public class CommonDataParseUtil {

    public static <T> T parseCommonData(String resultStr, Class<T> t) {

        if (TextUtils.isEmpty(resultStr))
            return null;

        try {

            return new Gson().fromJson(resultStr, t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解决gson可能出现的多态问题
     *
     * @return
     */
    public static <T> T parseCommonData(String resultStr, Class<T> t, Type baseType, Object typeAdapter) {

        if (TextUtils.isEmpty(resultStr))
            return null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(baseType, typeAdapter)
                .create();
        return gson.fromJson(resultStr, t);

    }


}
