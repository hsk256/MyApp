package com.creativeboy.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.adapter.MyCarRecyclerviewAdapter;
import com.creativeboy.myapp.adapter.OnRecyclerviewItemClickListener;
import com.creativeboy.myapp.app.Constants;
import com.creativeboy.myapp.utils.SnackbarUtil;

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
//        car_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),3));
        car_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        myCarRecyclerviewAdapter = new MyCarRecyclerviewAdapter(imgs);
        car_recyclerview.setAdapter(myCarRecyclerviewAdapter);
        myCarRecyclerviewAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SnackbarUtil.showLong(view,"click "+position);
            }
        });
    }
}
