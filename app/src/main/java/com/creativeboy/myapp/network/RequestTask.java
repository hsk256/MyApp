package com.creativeboy.myapp.network;

import com.android.volley.VolleyError;

/**
 * Created by heshaokang on 2015/9/8.
 * volley 请求时的回调
 */
public interface RequestTask<T> {
    /**
     * 请求前
     */
    public void onPreExcute();

    /**
     * 请求成功
     * @param response
     */
    public void onSuccess(T response);

    /**
     * 失败
     * @param error
     */
    public void onError(VolleyError error);
    /**
     * 超时
     */
    public void onTimeOut();

    /**
     * 未联网
     */
    public void onDisconnected();
}
