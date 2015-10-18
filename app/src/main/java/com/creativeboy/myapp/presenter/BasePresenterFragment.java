package com.creativeboy.myapp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativeboy.myapp.view.Vu;

/**
 * Created by heshaokang on 2015/10/17.
 * Fragment作为Presenter的抽象类
 */
public abstract class BasePresenterFragment<V extends Vu> extends Fragment{
    protected V vu;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            vu = getVuClass().newInstance();
            vu.init(inflater,container);
            onBindVu();
            view = vu.getView();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDestroyVu();
        vu =null;
    }

    protected abstract Class<V> getVuClass();
    protected void onBindVu(){};
    protected void onDestroyVu(){};
}
