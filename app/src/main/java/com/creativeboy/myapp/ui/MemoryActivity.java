package com.creativeboy.myapp.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.creativeboy.myapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by heshaokang on 2015/10/16.
 * 内存优化模块
 */
public class MemoryActivity extends AppCompatActivity{
    @Bind(R.id.getMaxMemory)
    Button getMaxMemory;
    @Bind(R.id.tv_memory)
    TextView tv_memory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.getMaxMemory)
    public void setGetMaxMemory() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memory = activityManager.getMemoryClass();
        tv_memory.setText("最大可用内存为:"+memory);
    }
}
