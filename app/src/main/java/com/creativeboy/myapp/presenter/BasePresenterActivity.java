package com.creativeboy.myapp.presenter;

import android.os.Binder;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.creativeboy.myapp.view.Vu;

/**
 * Created by heshaokang on 2015/10/17.
 */
public abstract class BasePresenterActivity<V extends Vu> extends AppCompatActivity{
    private V vu;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            fm = getSupportFragmentManager();
            vu = getVuClass().newInstance();
            vu.init(getLayoutInflater(),null);
            setContentView(vu.getView());
            onBindVu();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(vu!=null) {
            vu = null;
        }
        onDestroyVu();
    }

    protected abstract Class<V> getVuClass();
    protected  void onBindVu(){};
    protected  void onDestroyVu(){};
}
