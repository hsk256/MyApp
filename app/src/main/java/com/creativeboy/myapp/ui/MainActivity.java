package com.creativeboy.myapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.utils.SnackbarUril;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
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
    private static String[] mTitles = {"Tab1","Tab2","Tab3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        onNavigationViewItemSelected(mNavigationView);
        //设置头像
        iv_header.setImageURI(Uri.parse("http://www.touxiang.cn/uploads/20120723/23-033215_282.jpg"));
        floatingActionButton.setOnClickListener(this);


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
