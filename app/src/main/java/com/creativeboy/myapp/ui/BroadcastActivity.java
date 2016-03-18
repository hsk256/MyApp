package com.creativeboy.myapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.creativeboy.myapp.R;

import java.lang.ref.WeakReference;

/**
 * Created by heshaokang on 2015/10/24.
 * 广播机制练习
 */
public class BroadcastActivity extends AppCompatActivity{
    private IntentFilter intentFilter;
    private NetWorkChangeReceiver netWorkChangeReceiver;
    private static String TAG = "BroadcastActivity";
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver(this);
        registerReceiver(netWorkChangeReceiver,intentFilter);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BroadcastActivity.this, ViewPagerActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
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
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG,"触发OnSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        unregisterReceiver(netWorkChangeReceiver);
    }

     static class NetWorkChangeReceiver extends BroadcastReceiver {
        private final WeakReference<BroadcastActivity> activityWeakReference;

         NetWorkChangeReceiver(BroadcastActivity activity) {
             activityWeakReference = new WeakReference<BroadcastActivity>(activity);
         }

         @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
             if(networkInfo!=null &&networkInfo.isAvailable()) {
                 Toast.makeText(context,"网络正常",Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(context,"网络不可用",Toast.LENGTH_SHORT).show();
             }

        }
    }
}
