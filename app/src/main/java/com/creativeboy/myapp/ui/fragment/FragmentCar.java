package com.creativeboy.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.adapter.MyCarRecyclerviewAdapter;
import com.creativeboy.myapp.app.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by heshaokang on 2015/9/9.
 * 新闻Fragment
 */
public class FragmentCar extends Fragment{
    @Bind(R.id.car_recyclerview)
    RecyclerView car_recyclerview;
    private MyCarRecyclerviewAdapter myCarRecyclerviewAdapter;
    private String[] imgs= Constants.IMAGES_URL;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars,container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        car_recyclerview.setLayoutManager(linearLayoutManager);
        myCarRecyclerviewAdapter = new MyCarRecyclerviewAdapter(imgs);
        car_recyclerview.setAdapter(myCarRecyclerviewAdapter);
    }
}
