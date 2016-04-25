package com.creativeboy.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.adapter.BaseAdapter;
import com.creativeboy.myapp.adapter.ViewHolder;
import com.creativeboy.myapp.model.bean.Joke;
import com.creativeboy.myapp.presenter.JokePresesnter;
import com.creativeboy.myapp.view.JokeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by heshaokang on 2015/9/9.
 * 新闻Fragment
 */
public class FragmentJoke extends Fragment implements JokeView,SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "FragmentJoke";
    @Bind(R.id.joke_recyclerview)
    RecyclerView joke_recyclerview;
    @Bind(R.id.ll_loading_error)
    LinearLayout linearLayout;
    @Bind(R.id.joke_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Joke> dataList = new ArrayList<>();
    private JokePresesnter jokePresesnter;
    private LinearLayoutManager linearLayoutManager;
    private static String number = "10"; //分页大小
    private static int page = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        joke_recyclerview.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        //加载颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_blue_dark);
        dataList.add(new Joke("12","23","http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg","http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg","ds"));
        dataList.add(new Joke("123","faf","http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg","http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg","df"));
        jokePresesnter = new JokePresesnter(this);
        joke_recyclerview.setAdapter(new BaseAdapter<Joke>(getActivity(),R.layout.item_joke,dataList) {
            @Override
            public void convert(ViewHolder holder, Joke joke) {
                holder.setText(R.id.tv_title,joke.getTitle());
                holder.setImageByFresc(R.id.sd_pic,joke.getPicUrl());
            }

        });




    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setData(List<Joke> data) {
        swipeRefreshLayout.setRefreshing(false);
        if(!data.isEmpty()) {
            Log.d(TAG, "data--" + data);
            dataList.addAll(data);
            Log.d(TAG, "data--" + dataList.get(0).getDescription()+dataList.get(0).getTime());
        }else {
            showErrorLayout();
        }

    }

    @Override
    public void loadError() {
        dataList.clear();
        swipeRefreshLayout.setRefreshing(false);
        showErrorLayout();
    }

    private void showErrorLayout() {
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onRefresh() {
    }
}
