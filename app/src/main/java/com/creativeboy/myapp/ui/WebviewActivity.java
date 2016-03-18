package com.creativeboy.myapp.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.utils.Log;

import java.io.FileOutputStream;

/**
 * Created by heshaokang on 2016/3/18.
 */
public class WebviewActivity extends AppCompatActivity{
    private static final String TAG = "WebviewActivity";
    private Button btn_capture;
    private WebView wv_capture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.enableSlowWholeDocumentDraw();
        setContentView(R.layout.activity_webview_capture);
        btn_capture = (Button) findViewById(R.id.btn_capture);
        wv_capture = (WebView) findViewById(R.id.wv_capture);
        initView();

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picture picture = wv_capture.capturePicture();
//                int width = picture.getWidth();
//                int height = picture.getHeight();
//                if (width > 0 && height > 0) {
//                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(bitmap);
//                    picture.draw(canvas);

                float scale = wv_capture.getScale();
                int webViewHeight = (int) (wv_capture.getContentHeight()*scale+0.5);
                Bitmap bitmap = Bitmap.createBitmap(wv_capture.getWidth(),webViewHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                wv_capture.draw(canvas);
                    try {
                        String fileName = "/sdcard/webview_capture.jpg";
                        FileOutputStream fos = new FileOutputStream(fileName);
                        //压缩bitmap到输出流中
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
                        fos.close();
                        Toast.makeText(WebviewActivity.this, "CAPTURE SUCCESS", Toast.LENGTH_LONG).show();
                        bitmap.recycle();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

//            }
        });

    }

    private void initView() {
        WebSettings webSettings = wv_capture.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true); //支持缩放
        wv_capture.requestFocusFromTouch();
        wv_capture.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });
        wv_capture.loadUrl("http://www.baidu.com");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK)&&wv_capture.canGoBack()) {
            wv_capture.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
