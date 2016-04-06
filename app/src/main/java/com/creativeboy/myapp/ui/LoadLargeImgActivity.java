package com.creativeboy.myapp.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.view.customeView.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by heshaokang on 2015/10/16.
 * 内存优化模块
 */
public class LoadLargeImgActivity extends AppCompatActivity{
    @Bind(R.id.id_largetImageview)
    LargeImageView largeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_large_img);
        ButterKnife.bind(this);

        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            largeImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
