package com.creativeboy.myapp.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by heshaokang on 2015/9/6.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Fresco.initialize(getApplicationContext());
    }
}
