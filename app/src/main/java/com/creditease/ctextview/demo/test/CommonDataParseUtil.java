package com.creditease.ctextview.demo.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by zhxh on 2019/09/27
 */
public class CommonDataParseUtil {

    public static boolean isSucceed(String resultStr) {

        boolean isSucceed = false;

        if (CommonUtils.isNull(resultStr))
            return isSucceed;

        try {

            JSONObject json = new JSONObject(resultStr);

            String status = json.getString("status");

            if (status != null && "success".equals(status)) {

                isSucceed = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isSucceed;
    }

    public static CommonData getData(String resultStr) {

        if (CommonUtils.isNull(resultStr))
            return null;

        CommonData commonData = new CommonData();

        try {

            JSONObject json = new JSONObject(resultStr);

            String status = null;
            if (!json.isNull("status"))
                status = json.getString("status");

            if (status != null && "success".equals(status)) {

                commonData.setSuccessBoo(true);
            } else {

                commonData.setSuccessBoo(false);
            }

            if (!json.isNull("info"))
                commonData.setInfo(json.getString("info"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return commonData;
    }

    /***
     * 返回状态信息
     * @param resultStr
     * @return
     */
    public static CommonData getResposeData(String resultStr) {

        CommonData commonData = new CommonData();

        if (CommonUtils.isNull(resultStr))
            return commonData;

        try {

            JSONObject json = new JSONObject(resultStr);

            if (!json.isNull("action"))
                commonData.setAction(json.getString("action"));

            if (!json.isNull("code"))
                commonData.setResultCode(json.getInt("code"));

            if (!json.isNull("message"))
                commonData.setMessage(json.getString("message"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return commonData;
    }

    public static <T> T parseCommonData(String resultStr, Class<T> t) {

        if (CommonUtils.isNull(resultStr))
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

        if (CommonUtils.isNull(resultStr))
            return null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(baseType, typeAdapter)
                .create();
        return gson.fromJson(resultStr, t);

    }

    /**
     * 解析加密的response
     *
     * @param resultStr response字符串
     * @param cls       要解析等的类 如 FundHistoryValueResponse.class
     * @param <T>       返回传进去的实体类 如 FundHistoryValueResponse.class
     * @return 返回传进去的实体类
     */
    public static <T> T parseDecryptData(String resultStr, Class<T> cls) {

        if (CommonUtils.isNull(resultStr))
            return null;

        T object;

        try {
            Gson gson = new Gson();
            object = gson.fromJson(TripleDesUtil.decryptMode(resultStr), cls);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }


}
