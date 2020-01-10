package com.zcl.practice.util;

import android.database.Cursor;

import java.io.Closeable;
import java.io.IOException;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 17/12/21 11:55
 */

public class Utility {
    
    private Utility() {}
    
    /**
     * 安全关闭
     * @param closeable
     */
    public static void closeSafely(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void close(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }
    
}
