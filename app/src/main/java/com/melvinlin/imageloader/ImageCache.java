package com.melvinlin.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by melvin on 2018/3/26.
 */

public class ImageCache {
    // 圖片快取
    LruCache<String, Bitmap> mImageCache;

    public ImageCache(){
        initImageCache();
    }

    private void initImageCache() {
        // 計算可使用的最大記憶體
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 取1/4的可用記憶體作為快取
        final int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    public void put(String url, Bitmap bitmap){
        mImageCache.put(url, bitmap);
    }

    public Bitmap get(String url){
        return mImageCache.get(url);
    }
}
