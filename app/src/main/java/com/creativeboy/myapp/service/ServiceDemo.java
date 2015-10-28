package com.creativeboy.myapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.creativeboy.myapp.utils.Log;

import service.MyServiceAIDLDemo;

/**
 * Created by heshaokang on 2015/10/28.
 */
public class ServiceDemo extends Service{
    private static final String TAG="ServiceDemo";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mBind;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCraete");
        Log.d(TAG, "MyService thread id is " + Thread.currentThread().getId());
        Log.d(TAG, "process id is " + android.os.Process.myPid());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
     public static class MyBind extends Binder {
        public void startDown() {
            Log.d(TAG,"startDown execute");
        }
    }

    MyServiceAIDLDemo.Stub mBind = new MyServiceAIDLDemo.Stub(){


        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int plus(int a, int b) throws RemoteException {
            return a+b;
        }
    };
}
