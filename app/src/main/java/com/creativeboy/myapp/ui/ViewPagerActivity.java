package com.creativeboy.myapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.utils.Utils;
import com.creativeboy.myapp.view.customeView.HorizontalScrollViewEx;

import java.util.ArrayList;


/**
 * Created by heshaokang on 2016/3/13.
 */
public class ViewPagerActivity extends AppCompatActivity{

    private static final String TAG = "ViewPagerActivity";
    private HorizontalScrollViewEx mListContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_activity_layout);
        initView();
    }

    private void initView() {

        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
        final int screenWidth = Utils.getScreenMetrics(this).widthPixels;
        final int screenHeight = Utils.getScreenMetrics(this).heightPixels;
        for(int i=0;i<3;i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout,mListContainer,false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page "+(i+1));
            layout.setBackgroundColor(Color.rgb(255/(i+1),255/(i+1),0));
            createList(layout);
            mListContainer.addView(layout);
        }

    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();

        for(int i=0;i<50;i++) {
            datas.add("name "+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item,R.id.name,datas);

        listView.setAdapter(adapter);
    }
}
