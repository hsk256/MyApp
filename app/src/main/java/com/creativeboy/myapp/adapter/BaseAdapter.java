package com.creativeboy.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by littlekang on 16/4/21.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    protected Context context;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public BaseAdapter(Context context,int layoutId,List<T> data) {
        this.context = context;
        mInflater.from(context);
        mLayoutId = layoutId;
        mDatas = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(context,null,parent,mLayoutId,-1);
        setListener(parent,viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder,mDatas.get(position));
    }
    public abstract void convert(ViewHolder holder,T t);
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 设置监听器
     * @param parent
     * @param viewHolder
     */
    protected void setListener(final ViewGroup parent,final ViewHolder viewHolder) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent,v,mDatas.get(position),position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemClickListener!=null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemLongClick(parent,v,mDatas.get(position),position);
                }
                return false;
            }
        });
    }

    public int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }


}
