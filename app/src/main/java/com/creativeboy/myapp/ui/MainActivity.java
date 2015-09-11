package com.creativeboy.myapp.ui;

import android.net.Uri;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.adapter.MyViewPagerAdapter;
import com.creativeboy.myapp.presenter.JokePresesnter;
import com.creativeboy.myapp.ui.fragment.FragmentCar;
import com.creativeboy.myapp.ui.fragment.FragmentJoke;
import com.creativeboy.myapp.ui.fragment.FragmentNews;
import com.creativeboy.myapp.utils.Log;
import com.creativeboy.myapp.utils.SnackbarUril;
import com.facebook.drawee.view.SimpleDraweeView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private static final String TAG = "MainActivity";
    @Bind(R.id.navigation)
    NavigationView mNavigationView;
    @Bind(R.id.draw_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_header)
    SimpleDraweeView iv_header;
    @Bind(R.id.main_content)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.appbarlayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.floatingbutton)
    FloatingActionButton floatingActionButton;
    private List<Fragment> fragmentList;
    private FragmentCar fragmentCar;
    private FragmentNews fragmentNews;
    private FragmentJoke fragmentJoke;
    private static String[] mTitles = {"新闻","娱乐","汽车"};
    private MyViewPagerAdapter myViewPagerAdapter;
    private JokePresesnter jokePresesnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        onNavigationViewItemSelected(mNavigationView);
        //设置头像
        iv_header.setImageURI(Uri.parse("http://www.touxiang.cn/uploads/20120723/23-033215_282.jpg"));
        floatingActionButton.setOnClickListener(this);

        fragmentCar = new FragmentCar();
        fragmentJoke = new FragmentJoke();
        fragmentNews = new FragmentNews();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentJoke);
        fragmentList.add(fragmentCar);
        fragmentList.add(fragmentNews);

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),mTitles,fragmentList);
        viewPager.setAdapter(myViewPagerAdapter);
        //设置viewpager 最大的缓存页面数
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);
        //将viewpager 与 Tablayout关联起来
        tabLayout.setupWithViewPager(viewPager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        tabLayout.setTabsFromPagerAdapter(myViewPagerAdapter);

    }

    private void onNavigationViewItemSelected(NavigationView nav) {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_settings:

                        break;
                }
                menuItem.setCheckable(true);
                //关闭抽屉
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.floatingbutton:
                SnackbarUril.showShort(v,"floatting action button");
        }
    }
}
