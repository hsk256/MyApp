package com.creativeboy.myapp.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.service.ServiceDemo;
import com.creativeboy.myapp.utils.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.MyServiceAIDLDemo;

/**
 * Created by heshaokang on 2015/10/28.
 */
public class ServiceActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.startService)
    Button startService;
    @Bind(R.id.stopService)
    Button stopService;
    @Bind(R.id.bindService)
    Button bindService;
    @Bind(R.id.unBindService)
    Button unBindService;
    private static final String TAG="ServiceDemo";
    private ServiceDemo.MyBind myBind;
    private MyServiceAIDLDemo myServiceAIDLDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        Log.d(TAG, "ServiceActivity thread id is " + Thread.currentThread().getId());
        Log.d(TAG, "process id is " + android.os.Process.myPid());
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unBindService.setOnClickListener(this);

    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected");
            myServiceAIDLDemo = MyServiceAIDLDemo.Stub.asInterface(service);
            int result = 0;
            try {
                result = myServiceAIDLDemo.plus(1,2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"result--"+result);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisConnected");
        }
    };
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.startService:
                intent = new Intent(ServiceActivity.this, ServiceDemo.class);
                startService(intent);
                break;
            case R.id.stopService:
                intent = new Intent(ServiceActivity.this,ServiceDemo.class);
                stopService(intent);
                break;
            case R.id.bindService:
                intent = new Intent(ServiceActivity.this,ServiceDemo.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unBindService:
                intent = new Intent(ServiceActivity.this,ServiceDemo.class);
                unbindService(connection);
                break;
        }
    }

}
