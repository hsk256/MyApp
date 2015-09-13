package com.creativeboy.myapp.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.model.bean.Joke;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by heshaokang on 2015/9/11.
 */
public class MyJokeRecyclerviewAdapter extends RecyclerView.Adapter<MyJokeRecyclerviewAdapter.ViewHolder>{
    private List<Joke> datas;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_time,tv_content;
        public ViewHolder(View v) {
            super(v);
        }
        SimpleDraweeView sd_pic;


    }
    public MyJokeRecyclerviewAdapter(List<Joke> datas) {
        this.datas = datas;
    }

    /**
     * 创建viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.sd_pic = (SimpleDraweeView) view.findViewById(R.id.sd_pic);
        viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
        viewHolder.tv_time = (TextView) view.findViewById(R.id.tv_time);
        viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        return viewHolder;
    }

    /**
     * 将数据绑定viewholder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sd_pic.setImageURI(Uri.parse(datas.get(position).getPicUrl()));
        holder.tv_content.setText(datas.get(position).getDescription()+"...");
        holder.tv_title.setText(datas.get(position).getTitle());
        holder.tv_time.setText(datas.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
