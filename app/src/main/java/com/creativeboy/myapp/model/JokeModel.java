package com.creativeboy.myapp.model;

import com.android.volley.VolleyError;
import com.creativeboy.myapp.network.RequestManager;
import com.creativeboy.myapp.network.RequestTask;
import com.creativeboy.myapp.utils.Log;

import java.util.HashMap;

/**
 * Created by heshaokang on 2015/9/11.
 */
public class JokeModel {
    private static final String TAG = "JokeModel";

    public void getJokeInfo(String url,HashMap<String,String> req, final RequestTask requestTask) {
        RequestManager.StringPost(url, req, new RequestTask<String>() {
            @Override
            public void onPreExcute() {
            }

            @Override
            public void onSuccess(String response) {
                requestTask.onSuccess(response);
            }

            @Override
            public void onError(VolleyError error) {
                requestTask.onError(error);
            }

            @Override
            public void onTimeOut() {
                requestTask.onTimeOut();
            }

            @Override
            public void onDisconnected() {

            }
        });
    }
}
