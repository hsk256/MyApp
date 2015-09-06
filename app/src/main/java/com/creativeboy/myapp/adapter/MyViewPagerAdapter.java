package com.creativeboy.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * Created by heshaokang on 2015/9/6.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter{
    private String[] mTitles;
    private List<Fragment> mFragments;
    public MyViewPagerAdapter(FragmentManager fm,String[] mTitles,List<Fragment> mFragments) {
        super(fm);
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
