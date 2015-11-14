package com.creativeboy.myapp.utils.BitmapUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static final int DISK_CACHE_INDEX = 0;
    private static final int IO_BUFFER_SIZE = 8*1024;
    //磁盘缓存是否创建
    private boolean mIsDiskLruCacheCreated = false;

    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"ImageLoader#"+mCount.getAndIncrement());
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),sThreadFactory);


    private static Handler mMainHandler = new  Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoaderResult loaderResult = (LoaderResult) msg.obj;
            ImageView imageView = loaderResult.imageView;
            imageView.setImageBitmap(loaderResult.bitmap);
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if(uri.equals(loaderResult.uri)) {
                imageView.setImageBitmap(loaderResult.bitmap);
            }else {
                Log.d(TAG, "set image bitmap,but url has changed, ignored!");
            }
        }
    };

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
     * 异步方法
     * @param uri
     * @param imageView
     */
    public void bindBitmap(final String uri,final ImageView imageView) {
        bindBitmap(uri, imageView, 0, 0);
    }

    public void bindBitmap(final String uri,final ImageView imageView,final int reqWidth,final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if(bitmap!=null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable loadBitmapTask = new Runnable() {

            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);

                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }


    /**
     * load bitmap from memory cache or disk cache or http
     * 同步的 需要在子线程中调用
     * @param uri
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromMemCache,url:" + uri);
            return bitmap;
        }

        try {
            bitmap = loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
            if (bitmap != null) {
                Log.d(TAG, "loadBitmapFromDisk,url:" + uri);
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
            Log.d(TAG, "loadBitmapFromHttp,url:" + uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null && !mIsDiskLruCacheCreated) {
            Log.d(TAG, "encounter error, DiskLruCache is not created.");
            bitmap = downloadBitmapFromUrl(uri);
        }

        return bitmap;
    }


    /**
     * 从内存缓存加载bitmap
     * @param uri
     * @return
     */
    private Bitmap loadBitmapFromMemCache(String uri) {
        final String key = hashKeyForDisk(uri);
        Bitmap bitmap = getBitmapMemoryCache(key);
        return bitmap;
    }

    /**
     * 从网络下载图片 并将图片保存到磁盘缓存中 然后返回磁盘缓存中的图片
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     * @throws IOException
     */
    private Bitmap loadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException{
        if(Looper.myLooper()==Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network on ui thread");
        }
        if(mDiskLruCache==null) {
            return null;
        }

        String key = hashKeyForDisk(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if(editor!=null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if(downloadUrlToStream(url,outputStream)) {
                editor.commit(); //写入缓存
            }else {
                editor.abort();
            }
            //同步日志文件
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url,reqWidth,reqHeight);

    }

    /**
     * 从本地缓存中加载Bitmap
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException{

        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.d(TAG, "load bitmap from UI Thread, it's not recommended!");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyForDisk(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if(snapshot!=null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(fileDescriptor,reqWidth,reqHeight);
            if(bitmap!=null) {
                //添加到内存缓存中
                addBitmapToMemoryCache(key,bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 将网络图片下载下来
     * @param urlString
     * @param outputStream
     * @return
     */
    public boolean downloadUrlToStream(String urlString,OutputStream outputStream) {

        HttpURLConnection mHttpUrlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            mHttpUrlConnection = (HttpURLConnection) url.openConnection();
            in= new BufferedInputStream(mHttpUrlConnection.getInputStream(),IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);
            int b;
            while((b=in.read())!=-1) {
                out.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(mHttpUrlConnection!=null) {
                mHttpUrlConnection.disconnect();
            }
            Utils.close(in);
            Utils.close(out);
        }
        return false;
    }

    /**
     * 从网络下载图片 并返回bitmap
     * @param urlString
     * @return
     */
    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            Log.e(TAG, "Error in downloadBitmap: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            Utils.close(in);
        }
        return bitmap;
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
     * 封装的Result类 包含图片的地址 图片以及绑定的imgageview
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
