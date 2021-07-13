package com.zcl.library.net;

import android.util.Log;

public class LogUtils {

    public static int VERBOSE = 5;
    public static int DEBUG = 4;
    public static int INFO = 3;
    public static int WARN = 2;
    public static int ERROR = 1;
    /**
     * LOG_LEVEL < 5 关闭日志
     */
    private static int LOG_LEVEL = 6;

    private static boolean nativeLog = false;

    public static void v(String tag, String msg) {
        if (LOG_LEVEL > VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_LEVEL > INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_LEVEL > WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(tag, msg);
        }
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL > WARN) {
            Log.w(tag, msg, tr);
        }
        return -1;
    }

    public static boolean isOpen() {
        return LOG_LEVEL > VERBOSE;
    }

}
