package com.zcl.practice;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zcl.practice.plugin.LoadUtil;

/**
 * Description Application
 * Author ZhangChenglong
 * Date 17/12/21 11:43
 */

public class App extends Application {
    
    public static final boolean GLOBAL_DEBUG = BuildConfig.DEBUG;
    /** Application context */
    private static Context sAppContext;
    
    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        Fresco.initialize(this);
        LoadUtil.loadClass(this);
    }
    
    public static Context getAppContext() {
        return sAppContext;
    }
}
