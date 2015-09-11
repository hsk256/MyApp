package com.creativeboy.myapp.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creativeboy.myapp.model.BaseResponseDto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by heshaokang on 2015/9/8.
 */
public class RequestManager {

    /**
     * Post 请求
     * @param url
     * @param params 参数
     * @param clazz  对象类型
     * @param requestTask 回调
     * @return
     */
    public static <T extends BaseResponseDto> GsonRequest<T> Gsonpost(
            final String url,
            final HashMap<String,String> params,
            final Class<T> clazz,
            final RequestTask requestTask)
    {

        GsonRequest<T> gsonRequest;
        gsonRequest = new GsonRequest<T>(url, clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                requestTask.onSuccess(response);
            }}, errorListener(requestTask)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        VolleyManager.addRequestQueue(gsonRequest);
        return gsonRequest;

    }

    /**
     *  Get 请求
     * @param url
     * @param params
     * @param clazz
     * @param requestTask
     * @return
     */
    public static <T extends BaseResponseDto> GsonRequest<T> Gsonget(
            final String url,
            final HashMap<String,String> params,
            final Class<T> clazz,
            final RequestTask requestTask
    ) {
        GsonRequest<T> gsonRequest;
        gsonRequest = new GsonRequest<T>(getRequestParam(url,params), clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                requestTask.onSuccess(response);
            }}, errorListener(requestTask));
        VolleyManager.addRequestQueue(gsonRequest);
        return gsonRequest;
    }


    public static void StringPost(
            final String url,
            final HashMap<String,String> params,
            final RequestTask requestTask
    ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestTask.onSuccess(response);
            }
        },errorListener(requestTask)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        VolleyManager.addRequestQueue(stringRequest);
    }

    /**
     *  处理错误时的回调
     * @param requestTask
     * @return
     */
    public static Response.ErrorListener errorListener( final RequestTask requestTask) {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NoConnectionError) { //无网络连接
                    requestTask.onDisconnected();
                }else if(error instanceof TimeoutError) { //连接超时
                    requestTask.onTimeOut();
                }else {
                    requestTask.onError(error);
                }
            }
        };
    }

    /**
     * get请求时 拼接get Url
     * @param url
     * @param param
     * @return
     */
    public static String getRequestParam(String url,HashMap<String,String> param) {
        if(param==null||param.size()==0) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder("?");
        int index = 0;
        Iterator<Map.Entry<String,String>> entryIterator = param.entrySet().iterator();
        while(entryIterator.hasNext()) {
            Map.Entry<String,String> entry = entryIterator.next();
            if(index!=0) {
                stringBuilder.append("&");
            }
            try {
                stringBuilder
                        .append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                index++;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return url+stringBuilder.toString();
    }


}
