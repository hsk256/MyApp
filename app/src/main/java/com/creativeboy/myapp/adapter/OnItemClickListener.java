package com.creativeboy.myapp.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by littlekang on 16/4/21.
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewGroup parent, View view, T t, int position);
    boolean onItemLongClick(ViewGroup parent,View view,T t,int position);

}
