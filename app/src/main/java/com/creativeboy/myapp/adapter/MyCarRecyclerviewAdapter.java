package com.creativeboy.myapp.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativeboy.myapp.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by heshaokang on 2015/9/12.
 */
public class MyCarRecyclerviewAdapter extends RecyclerView.Adapter<MyCarRecyclerviewAdapter.ViewHolder>{
    private String[] imgs;

    public MyCarRecyclerviewAdapter(String[] imgs) {
        this.imgs = imgs;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cars,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.img = (SimpleDraweeView) view.findViewById(R.id.img);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setImageURI(Uri.parse(imgs[position]));
    }

    @Override
    public int getItemCount() {
        return imgs.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
        SimpleDraweeView img;
    }
}
