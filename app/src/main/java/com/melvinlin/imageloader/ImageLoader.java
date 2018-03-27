package com.melvinlin.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by melvin on 2018/3/26.
 */

public class ImageLoader {
    // 圖片快取
    ImageCache mImageCache = new ImageCache();
    // SD卡快取
    DiskCache mDiskCache = new DiskCache();
    // 是否使用SD卡快取
    boolean isUseDiskCache = false;

    // 執行緒池，執行緒數量為 CPU 的數量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // 加載圖片
    public void displayImage(final String url, final ImageView imageView){
        Bitmap bitmap = isUseDiskCache ? mDiskCache.get(url) : mImageCache.get(url);

        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);

        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);
                if(bitmap == null){
                    return;
                }

                if(imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url, bitmap);
            }
        });
    }

    public void setUseDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    public Bitmap downloadImage(String imageUrl){
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
