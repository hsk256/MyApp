package com.creativeboy.myapp.utils;

/**
 * Created by heshaokang on 2015/9/7.
 * 打印日志的统一类
 * DEBUG为是否开启调试模式的开关
 * true开启 false不开启 则不会输出log信息
 */
public class Log {
    private static boolean DEBUG = true;

    public static void i(String key,String value) {
        if(DEBUG) {
            android.util.Log.i(key,value);
        }
    }

    public static void d(String key,String value) {
        if(DEBUG) {
            android.util.Log.d(key,value);
        }
    }

    public static void e(String key,String value) {
        if(DEBUG) {
            android.util.Log.e(key,value);
        }
    }
    public static void v(String key,String value) {
        if(DEBUG) {
            android.util.Log.v(key,value);
        }
    }
}
