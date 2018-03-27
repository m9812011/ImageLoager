package com.melvinlin.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by melvin on 2018/3/27.
 */

public class DiskCache {
    public static String cacheDir = "sdcard/cache/";

    // 從快取中獲取圖片
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir + url);
    }

    // 將圖片快取到記憶體中
    public void put(String url, Bitmap bmp){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + url);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
