package com.creativeboy.myapp.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Created by heshaokang on 2015/9/8.
 * 自定义vooley request 将返回的数据用Gson解析为对象
 */
public class GsonRequest<T> extends Request<T>{
    private  Listener<T> mListener;
    private  Class<T> clazz;
    private Gson gson = new Gson();
    public GsonRequest(String url,Class<T> clazz,Listener<T> listener,Response.ErrorListener errorListener) {
        this(Method.POST,url,clazz,listener,errorListener);

    }

    public GsonRequest(int method,String url,Class<T> clazz,Listener<T> listener,Response.ErrorListener errorListener) {
        super(method,url,errorListener);
        this.mListener = listener;
        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T obj = gson.fromJson(data,clazz);
            return Response.success(obj,HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
