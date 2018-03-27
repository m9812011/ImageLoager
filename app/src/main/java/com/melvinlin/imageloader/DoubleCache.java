package com.melvinlin.imageloader;

import android.graphics.Bitmap;

/**
 * 雙快取。獲取圖片時先從記憶體快取中獲取，如果記憶體中沒有快取該圖片，再從SD卡中獲取。
 * 快取圖片也是在記憶體和SD卡中都快取一份
 */
public class DoubleCache {
     ImageCache mMemoryCache = new ImageCache();
     DiskCache mDiskCache = new DiskCache();

     // 先從記憶體中快取獲取圖片，如果沒有再從SD卡中獲取
     public Bitmap get(String url){
         Bitmap bitmap = mMemoryCache.get(url);

         if(bitmap == null){
             bitmap = mDiskCache.get(url);
         }

         return bitmap;
     }

     // 將圖片快取到體和SD卡中
    public void put(String url, Bitmap bmp){
         mMemoryCache.put(url, bmp);
         mDiskCache.put(url, bmp);
    }
}
