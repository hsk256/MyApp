package com.creativeboy.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.adapter.MyJokeRecyclerviewAdapter;
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
public class FragmentJoke extends Fragment implements JokeView{
    private static final String TAG = "FragmentJoke";
    @Bind(R.id.joke_recyclerview)
    RecyclerView joke_recyclerview;
    private List<Joke> dataList;
    private RecyclerView.Adapter adapter;
    private JokePresesnter jokePresesnter;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        joke_recyclerview.setLayoutManager(linearLayoutManager);
        jokePresesnter = new JokePresesnter(this);
        jokePresesnter.getJokeInfo("6", "1");
        dataList = new ArrayList<>();

        adapter = new MyJokeRecyclerviewAdapter(dataList);
        joke_recyclerview.setAdapter(adapter);
    }


    @Override
    public void setData(List<Joke> data) {
        if(data!=null) {
            Log.d(TAG,"data--"+data);
            dataList.addAll(data);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void loadError() {

    }
}
