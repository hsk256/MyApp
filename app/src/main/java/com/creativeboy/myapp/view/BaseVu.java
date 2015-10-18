package com.creativeboy.myapp.view;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by heshaokang on 2015/10/17.
 */
public abstract class BaseVu implements Vu{
    LayoutInflater inflater;
    protected View vu;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        this.inflater = inflater;
    }

    /**
     * 设置view
     * @param view
     */
    protected final void setView(View view) {
        this.vu = view;
    }
    protected final void setView(@LayoutRes int res) {
        vu = inflater.inflate(res,null);
    }

    /**
     * 封装findViewById操作
     * @param view
     * @param id
     * @param <V>
     * @return
     */
    protected final <V extends View> V $(View view,@IdRes int id) {
        return (V)view.findViewById(id);
    }
    protected final <V extends View> V $(@IdRes int id) {
        return (V)vu.findViewById(id);
    }

    @Override
    public View getView() {
        return vu;
    }
}
