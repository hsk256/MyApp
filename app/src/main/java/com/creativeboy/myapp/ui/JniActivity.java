package com.creativeboy.myapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.ndk.NdkJniUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by heshaokang on 2015/9/15.
 */
public class JniActivity extends AppCompatActivity{
    @Bind(R.id.tv_jni)
    TextView tv_jni;
    static {
        System.loadLibrary("YanboberJniLibName");	//defaultConfig.ndk.moduleName
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        ButterKnife.bind(this);
        tv_jni.setText(NdkJniUtils.getCLanguageString());
    }
}
