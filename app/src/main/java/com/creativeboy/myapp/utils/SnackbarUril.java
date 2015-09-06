package com.creativeboy.myapp.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.creativeboy.myapp.R;

/**
 * Created by heshaokang on 2015/9/6.
 * md 的提示信息组件 出现在屏幕下方 类似于Toast
 */
public class SnackbarUril {
    private static Snackbar snackbar;
    public static void showShort(View view,String msg) {
        snackbar = Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
        set();
    }
    public static void showLong(View view,String msg) {
        snackbar = Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
        set();
    }
    private static void set() {
        snackbar.show();
        // Snackbar中有一个可点击的文字，这里设置点击所触发的操作。
        snackbar.setAction(R.string.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Snackbar在点击“关闭”后消失
                snackbar.dismiss();
            }
        });
    }
}
