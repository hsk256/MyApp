package com.creativeboy.myapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.creativeboy.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heshaokang on 2015/10/16.
 * 电池优化模块
 */
public class BatteryActivity extends AppCompatActivity{
    private static final String TAG = "BatteryActivity";
    private List<String> list  = new ArrayList<String>();
    private Button button;
    private EditText et_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        Log.d(TAG, "onCreate");
//        et_text = (EditText) findViewById(R.id.et_text);
        button = (Button) findViewById(R.id.battery_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BatteryActivity.this,BroadcastActivity.class));
            }
        });
//        for(int i=0;i<100000;i++) {
//            list.add("内存泄露");
//        }
//        new MyThread().start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "BatterActivity--onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "BatterActivity--onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
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
