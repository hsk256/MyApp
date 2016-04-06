package com.creativeboy.myapp.view.customeView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.creativeboy.myapp.utils.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by littlekang on 16/4/6.
 * 加载大图的控件 学习自鸿洋博客
 */
public class LargeImageView extends View {

    private static final String TAG = "LargeImageView";
    private BitmapRegionDecoder bitmapRegionDecoder;
    //图片宽 高
    private int mImageWidth,mImageHeight;
    //绘制区域
    private  Rect mRect = new Rect();

    private MoveGestureDetector moveGestureDetector;
    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    static {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }
//    public LargeImageView(Context context) {
//        this(context, null);
//    }

    public LargeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    /**
     *  初始化BitmapRegionDecoder 并获取图片宽和高
     * @param in
     */
    public void setInputStream(InputStream in) {
        try {
            bitmapRegionDecoder = BitmapRegionDecoder.newInstance(in,false);
            BitmapFactory.Options tempOptions = new BitmapFactory.Options();
            tempOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, tempOptions);
            mImageWidth = tempOptions.outWidth;
            mImageHeight = tempOptions.outHeight;
            //requestLayout();
            //invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void init() {
        moveGestureDetector = new MoveGestureDetector(getContext(),new MoveGestureDetector.SimpleMoveGestureDetector() {
            @Override
            public boolean onMove(MoveGestureDetector detector) {
                int moveX = (int) detector.getMoveX();
                int moveY = (int) detector.getMoveY();

                if(mImageWidth>getWidth()) {
                    mRect.offset(-moveX, 0);
                    checkWidth();
                    invalidate();
                }

                if (mImageHeight > getHeight())
                {
                    mRect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }

                return true;
            }
        });
    }

    private void checkWidth()
    {


        Rect rect = mRect;
        int imageWidth = mImageWidth;

        if (rect.right > imageWidth)
        {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0)
        {
            rect.left = 0;
            rect.right = getWidth();
        }
    }


    private void checkHeight()
    {

        Rect rect = mRect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        if (rect.bottom > imageHeight)
        {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0)
        {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveGestureDetector.onToucEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw");
        Bitmap bm = bitmapRegionDecoder.decodeRegion(mRect,options);
        canvas.drawBitmap(bm,0,0,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure");
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;
        mRect.left = imageWidth/2-width/2;
        mRect.top = imageHeight/2-height/2;
        mRect.right = mRect.left+width;
        mRect.bottom = mRect.top+height;
    }
}
