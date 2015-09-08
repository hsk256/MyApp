package com.creativeboy.myapp.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by heshaokang on 2015/9/8.
 * volley 管理类 初始化volley 请求队列等
 */
public class VolleyManager {
    private static RequestQueue mRequestQueue;

    /**
     * 初始化请求队列
     * @param context
     */
    public static void init(Context context) {
        if(mRequestQueue==null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
    }

    /**
     * 获取请求队列
     */
    public static RequestQueue getRequestQueue() {
        if(mRequestQueue!=null) {
            return mRequestQueue;
        }else {
            throw  new IllegalStateException("RequestQueue is not initialized");
        }
    }

    /**
     * 将请求 添加到请求队列中
     */
    public static void addRequestQueue(Request<?> request,Object tag) {
        if(tag!=null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    /**
     * 取消请求
     * @param tag
     */
    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
