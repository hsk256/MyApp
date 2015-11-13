package com.creativeboy.myapp.utils.BitmapUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.LruCache;
import android.widget.ImageView;

import com.creativeboy.myapp.R;
import com.creativeboy.myapp.utils.Log;
import com.creativeboy.myapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by heshaokang on 2015/11/13.
 * 图片加载类 提供如下功能
 * 1. 图片的异步和同步加载
 * 2. 图片压缩
 * 3. 内存缓存
 * 4. 磁盘缓存
 * 5. 网络获取图片
 */
public class ImageLoader {
    private static final String TAG="ImageLoader";
    public static final int MESSAGE_POST_RESULT=1;
    //CPU数量
    private static final int CPU_COUNT= Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE=CPU_COUNT+1;

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT*2+1;

    private static final long KEEP_ALIVE = 10L;
    //磁盘缓存大小 50M
    private static final long DISK_CACHE_SIZE=1024*1024*50;
    //磁盘缓存是否创建
    private boolean mIsDiskLruCacheCreated = false;

//    private static final int TAH_KEY_URI = R.id.imageloader_uri;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"ImageLoader#"+mCount.getAndIncrement());
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),sThreadFactory);


    private static class MainHandler extends  Handler {
        public MainHandler() {
            Looper.getMainLooper();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    }

    private Context mContext;
    private ImageResizer mImageResizer = new ImageResizer();
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private ImageLoader(Context context) {

        mContext = context.getApplicationContext();
        //app最大可用内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        //缓存大小
        int cacheSize = maxMemory/8;

        mMemoryCache = new LruCache<String,Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };

        File diskCacheDir = getDiskCacheDir(context,"bitmap");
        if(!diskCacheDir.exists()) {
            diskCacheDir.mkdir();
        }

        if(getUsableSpace(diskCacheDir)>DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * build a new instance of ImageLoader
     * @param context
     * @return ImageLoader
     */
    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    /**
     * 将bitmap添加到内存缓存中
     * @param key
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key,Bitmap bitmap) {
        if(getBitmapMemoryCache(key)==null) {
            mMemoryCache.put(key,bitmap);
        }
    }

    /**
     * 从内存缓存中获取bitmap
     * @param key
     * @return
     */
    private Bitmap getBitmapMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * load bitmap from memory cache or disk cache or http
     * @param uri
     * @param imageView
     */
    public void bindBitmap(final String uri,final ImageView imageView) {
        bindBitmap(uri,imageView,0,0);
    }

    public void bindBitmap(final String uri,final ImageView imageView,final int reqWidth,final int reqHeight) {


    }

    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
     * disk filename.
     * 将一个字符串转换为MD5值 作为缓存文件名
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * Get a usable cache directory (external if available, internal otherwise).
     * 获取可用缓存文件
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context,String uniqueName) {
        //判断外置存储是否可用
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachepath;
        if(externalStorageAvailable) {
            cachepath = context.getExternalCacheDir().getPath();
        }else {
            cachepath = context.getCacheDir().getPath();
        }
        Log.d(TAG,"cachePath--"+cachepath);
        return new File(cachepath+File.separator+uniqueName);
    }

    /**
     * 返回指定目录下的可用字节数
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getUsableSpace(File path) {
        //sdk 大于 9
        if (Utils.hasGingerbread()){
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return stats.getBlockSizeLong()*stats.getAvailableBlocksLong();
    }


    /**
     * 封装的Result类
     */
    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;
        public LoaderResult(ImageView imageView,String uri,Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }
}
