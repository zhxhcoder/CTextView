package com.creditease.ctextview.demo.test;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by zhxh on 2019/08/12
 */
public class BaseFinanceNetResp<T> {

    public String result; // 结果

    public String errorCode; // 错误码

    public String msg; // 错误信息

    public T data; // 数据

    @Override
    public String toString() {
        return "BaseFinanceNetResp{" +
                "result='" + result + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class StringAdapter implements JsonDeserializer<String> {

        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return json.getAsString();
            } catch (Exception e) {
                return json.toString();
            }
        }
    }
}
