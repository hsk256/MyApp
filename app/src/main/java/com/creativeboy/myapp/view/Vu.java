package com.creativeboy.myapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by heshaokang on 2015/10/17.
 * View层的bace 接口
 */
public interface Vu {
    /**
     * 初始化view的方法
     * @param inflater
     * @param container
     */
    void init(LayoutInflater inflater,ViewGroup container);

    /**
     * 获取view
     * @return
     */
    View getView();
}
