package com.creativeboy.myapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.creativeboy.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshaokang on 2015/10/16.
 * 电池优化模块
 */
public class BatteryActivity extends AppCompatActivity{
    private List<String> list  = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        for(int i=0;i<100000;i++) {
            list.add("内存泄露");
        }
        new MyThread().start();
    }
    public static class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(10*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
