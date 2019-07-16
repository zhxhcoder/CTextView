package com.creditease.ctextview;

/**
 * Created by zhxh on 2018/8/17
 */
public final class BtnUtils {

    private static long lastClickTime;

    public synchronized static boolean isQuickClick(int freezeTime) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < freezeTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
