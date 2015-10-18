package com.creativeboy.myapp.ui;

import android.view.Menu;
import android.view.MenuItem;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.presenter.BasePresenterActivity;
import com.creativeboy.myapp.view.MainActivityView;

public class MainActivity extends BasePresenterActivity<MainActivityView> {
    private static final String TAG = "MainActivity";

    @Override
    protected void onBindVu() {
        super.onBindVu();
    }

    @Override
    protected Class<MainActivityView> getVuClass() {
        return MainActivityView.class;
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


}
