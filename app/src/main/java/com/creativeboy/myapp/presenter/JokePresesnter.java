package com.creativeboy.myapp.presenter;

import android.support.v4.app.Fragment;

import com.android.volley.VolleyError;
import com.creativeboy.myapp.app.Constants;
import com.creativeboy.myapp.model.JokeModel;
import com.creativeboy.myapp.model.bean.Joke;
import com.creativeboy.myapp.network.RequestTask;
import com.creativeboy.myapp.utils.Log;
import com.creativeboy.myapp.view.JokeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by heshaokang on 2015/9/11.
 */
public class JokePresesnter {
    private static final String TAG = "JokePresesnter";
    private JokeModel jokeModel;
    private JokeView jokeView;
    public JokePresesnter(Fragment fragment) {
        jokeModel = new JokeModel();
        jokeView = (JokeView) fragment;
    }
    public void getJokeInfo(String number,String page) {
        DateFormat dateFormat =new SimpleDateFormat("yyyyMMddHHmmss");
        String time_stamp = dateFormat.format(new Date());
        HashMap<String,String> req = new HashMap<>();
        req.put("showapi_appid",Constants.SHOW_API_APP_ID);
        req.put("showapi_sign",Constants.SHOW_API_SIGN);
        req.put("showapi_timestamp",time_stamp);
        req.put("num",number);
        req.put("page", page);
        jokeModel.getJokeInfo(Constants.SHOW_API_JOKE, req, new RequestTask<String>() {
            @Override
            public void onPreExcute() {

            }

            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject = null;
                List<Joke> jokeList = new ArrayList<>();
                Joke joke;
                try {
                    jsonObject = new JSONObject(response);
                    Log.d(TAG, "show_api_error:" + jsonObject.getString("showapi_res_error"));
                    Log.d(TAG, "show_api_code:" + jsonObject.getString("showapi_res_code"));
                    if (jsonObject.getString("showapi_res_code").equals("-1")) {
                        jokeView.loadError();
                    }else {
                        JSONObject resBody = jsonObject.getJSONObject("showapi_res_body");
                        for(int i=0;i<resBody.length()-2;i++) {
                            JSONObject data = resBody.getJSONObject(i+"");
                            joke= new Joke();
                            Log.d(TAG, "desc--"+data);
                            joke.setDescription(data.getString("description"));
                            joke.setTitle(data.getString("title"));
                            joke.setTime(data.getString("time"));
                            joke.setPicUrl(data.getString("picUrl"));
                            joke.setUrl(data.getString("url"));
                            jokeList.add(joke);
                        }
                        jokeView.setData(jokeList);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG,"errorInfo--"+error.getMessage());
                jokeView.loadError();
            }

            @Override
            public void onTimeOut() {
                jokeView.loadError();
            }

            @Override
            public void onDisconnected() {
                jokeView.loadError();

            }
        });
    }

}
