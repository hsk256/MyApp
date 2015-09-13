package com.creativeboy.myapp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.creativeboy.myapp.adapter.MyJokeRecyclerviewAdapter;
import com.creativeboy.myapp.model.bean.Joke;
import com.creativeboy.myapp.presenter.JokePresesnter;
import com.creativeboy.myapp.view.JokeView;

import java.lang.ref.WeakReference;
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
    private RecyclerView.Adapter adapter;
    private JokePresesnter jokePresesnter;
    private LinearLayoutManager linearLayoutManager;
    private static String number = "10"; //分页大小
    private static int page = 1;
    private static int REFRESH_COMPLETE = 0x01;
    private static int LOADING_COMPLETE = 0x02;
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
        jokePresesnter = new JokePresesnter(this);
        adapter = new MyJokeRecyclerviewAdapter(dataList);
        joke_recyclerview.setAdapter(adapter);

        handler.sendEmptyMessageDelayed(REFRESH_COMPLETE,800);
    }

    private  Handler  handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==REFRESH_COMPLETE) {
                swipeRefreshLayout.setRefreshing(true);
                page = 1;
                jokePresesnter.getJokeInfo(number, String.valueOf(page));
            }

            super.handleMessage(msg);
        }
    };

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
            //joke_recyclerview.setLayoutManager(linearLayoutManager);
            adapter.notifyDataSetChanged();
        }else {
            showErrorLayout();
        }

    }

    @Override
    public void loadError() {
        swipeRefreshLayout.setRefreshing(false);
        showErrorLayout();
    }

    private void showErrorLayout() {
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessageDelayed(REFRESH_COMPLETE,800);
            }
        });
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(REFRESH_COMPLETE,800);
    }
}
