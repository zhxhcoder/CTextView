package com.creditease.ctextview.demo.test;

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
}
