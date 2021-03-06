package com.creativeboy.myapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by littlekang on 16/4/21.
 */
public class ViewHolder extends RecyclerView.ViewHolder{
    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews;
    private int mLayoutId;
    private int mPosition;
    public ViewHolder(Context context, View itemView,int position) {
        super(itemView);
        this.mContext = context;
        this.mConvertView = itemView;
        mConvertView.setTag(this);
        mViews = new SparseArray<View>();
        mPosition = position;

    }

    public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position) {

        ViewHolder viewHolder;
        if(convertView ==null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
            viewHolder = new ViewHolder(context,itemView,position);
            viewHolder.mLayoutId = layoutId;
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;

        }
        return viewHolder;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if(view==null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }

        return (T) view;
    }


    public View getConvertView() {
        return mConvertView;
    }

    public void updatePosition(int position) {
        mPosition = position;
    }
    /**
     *
     * @param viewId
     * @param text
     * @return
     * set textview
     */
    public ViewHolder setText(int viewId,String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }


    /**
     * 设置图片
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageByFresc(int viewId,String url) {
        SimpleDraweeView simpleDraweeView = getView(viewId);
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);
        return this;
    }





}
