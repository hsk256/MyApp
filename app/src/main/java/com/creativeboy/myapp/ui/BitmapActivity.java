package com.creativeboy.myapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.app.Constants;
import com.creativeboy.myapp.utils.BitmapUtil.ImageLoader;
import com.creativeboy.myapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by heshaokang on 2015/11/13.
 */
public class BitmapActivity extends AppCompatActivity implements AbsListView.OnScrollListener{
    @Bind(R.id.gridView)
    GridView mImageGridView;
    private static final String TAG="BitmapActivity";
    private List<String> mUrList = new ArrayList<String>();
    ImageLoader imageLoader;
    private boolean mIsGridViewIdle = true;
    private int mImageWidth = 0;
    private boolean isWifi = false;
    private boolean mCanGetBitmapFromNetWork = false;
    private BaseAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);
        initData();
        initView();
        imageLoader = ImageLoader.build(BitmapActivity.this);
    }

    private void initData() {
        int screenWidth = Utils.getScreenMetrics(this).widthPixels;
        int space = (int) Utils.dp2px(this,20f);
        mImageWidth = (screenWidth-space)/3;
        isWifi = Utils.isWifi(this);
        if(isWifi) {
            mCanGetBitmapFromNetWork = true;
        }
    }

    private void initView() {
        imageAdapter = new ImageAdapter(this);
        mImageGridView.setAdapter(imageAdapter);
        mImageGridView.setOnScrollListener(this);
        if(!isWifi) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("当前网络不是wifi，确认要下载图片吗");
            builder.setTitle("警告");
            builder.setNegativeButton("否", null);
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCanGetBitmapFromNetWork = true;
                    imageAdapter.notifyDataSetChanged();
                }
            });
            builder.show();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当滑动停止的时候加载图片
        if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsGridViewIdle = true;
            imageAdapter.notifyDataSetChanged();
        }else {
            mIsGridViewIdle = false;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Drawable mDefaultBitmapDrawable;

        private ImageAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mDefaultBitmapDrawable = context.getResources().getDrawable(R.mipmap.image_default);
        }


        @Override
        public int getCount() {
            return Constants.IMAGES_URL.length;
        }

        @Override
        public String getItem(int position) {
            return Constants.IMAGES_URL[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView==null) {
                convertView = mInflater.inflate(R.layout.image_list_item,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ImageView imageView = viewHolder.imageView;
            final String tag = (String)imageView.getTag();
            final String uri = getItem(position);
            if (!uri.equals(tag)) {
                imageView.setImageDrawable(mDefaultBitmapDrawable);
            }
            if (mIsGridViewIdle && mCanGetBitmapFromNetWork) {
                imageView.setTag(uri);
                imageLoader.bindBitmap(uri, imageView, mImageWidth, mImageWidth);
            }
            return convertView;
        }
    };
    private static class ViewHolder {
        public ImageView imageView;
    }
}
