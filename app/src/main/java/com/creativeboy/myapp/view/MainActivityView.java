package com.creativeboy.myapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.adapter.MyViewPagerAdapter;
import com.creativeboy.myapp.ui.BitmapActivity;
import com.creativeboy.myapp.ui.BroadcastActivity;
import com.creativeboy.myapp.ui.JniActivity;
import com.creativeboy.myapp.ui.MemoryActivity;
import com.creativeboy.myapp.ui.ServiceActivity;
import com.creativeboy.myapp.ui.ViewPagerActivity;
import com.creativeboy.myapp.ui.WebviewActivity;
import com.creativeboy.myapp.ui.fragment.FragmentCar;
import com.creativeboy.myapp.ui.fragment.FragmentJoke;
import com.creativeboy.myapp.ui.fragment.FragmentNews;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by heshaokang on 2015/10/17.
 */
public class MainActivityView implements Vu,ViewPager.OnPageChangeListener,View.OnClickListener{
    private  View view;
    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private SimpleDraweeView iv_header;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;
    private Context context;
    private List<Fragment> fragmentList;
    private FragmentCar fragmentCar;
    private FragmentNews fragmentNews;
    private FragmentJoke fragmentJoke;
    private static String[] mTitles = {"娱乐","图片","新闻"};
    private MyViewPagerAdapter myViewPagerAdapter;
    AppCompatActivity act;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.activity_main,container,false);
        mNavigationView = (NavigationView) view.findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.draw_layout);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        iv_header = (SimpleDraweeView) view.findViewById(R.id.iv_header);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.main_content);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbarlayout);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        this.context = inflater.getContext();
        setOnClickListener();
        setHeader();
        initToolbar();
        initDrawerLayout();
        initFragment();
        initViewPager();
    }


    /**
     * 初始化Toolbal
     */
    private void initToolbar() {
        act = (AppCompatActivity)context;
        if(mToolbar!=null) {
            act.setSupportActionBar(mToolbar);
            act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTitleTextColor(context.getResources().getColor(R.color.white));
    }

    /**
     * 初始化抽屉
     */
    private void initDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(act,drawerLayout,mToolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        onNavigationViewItemSelected(mNavigationView);
    }

    /**
     * 添加fragment
     */
    private void initFragment() {
        fragmentCar = new FragmentCar();
        fragmentJoke = new FragmentJoke();
        fragmentNews = new FragmentNews();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentJoke);
        fragmentList.add(fragmentCar);
        fragmentList.add(fragmentNews);
    }

    /**
     * viewPager setting
     */
    private void initViewPager() {
        myViewPagerAdapter = new MyViewPagerAdapter(act.getSupportFragmentManager(),mTitles,fragmentList);
        viewPager.setAdapter(myViewPagerAdapter);
        //设置viewpager 最大的缓存页面数
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);
        //将viewpager 与 Tablayout关联起来
        tabLayout.setupWithViewPager(viewPager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        tabLayout.setTabsFromPagerAdapter(myViewPagerAdapter);
    }

    /**
     * 侧边栏item监听时间
     * @param nav
     */
    private void onNavigationViewItemSelected(NavigationView nav) {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_jni:
                        context.startActivity(new Intent(context, JniActivity.class));
                        break;
                    case R.id.nav_menu_leak:
                        context.startActivity(new Intent(context, MemoryActivity.class));
                        break;
                    case R.id.id_broadcast:
                        context.startActivity(new Intent(context,BroadcastActivity.class));
                        break;
                    case R.id.id_service:
                        context.startActivity(new Intent(context,ServiceActivity.class));
                        break;
                    case R.id.nav_menu_bitmap:
                        context.startActivity(new Intent(context,BitmapActivity.class));
                        break;
                    case R.id.nav_menu_view_conflict:
                        context.startActivity(new Intent(context, ViewPagerActivity.class));
                        break;
                    case R.id.nav_menu_webview:
                        context.startActivity(new Intent(context, WebviewActivity.class));
                        break;
                }
                menuItem.setCheckable(true);
                //关闭抽屉
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setOnClickListener() {
    }
    /**
     * 设置头像
     */
    private void setHeader() {
        iv_header.setImageURI(Uri.parse("http://www.touxiang.cn/uploads/20120723/23-033215_282.jpg"));
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
