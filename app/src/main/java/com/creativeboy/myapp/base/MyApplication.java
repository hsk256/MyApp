package com.creativeboy.myapp.base;

import android.app.Application;

import com.creativeboy.myapp.network.VolleyManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;

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
        VolleyManager.init(getApplicationContext());
        LeakCanary.install(this);
    }
}
