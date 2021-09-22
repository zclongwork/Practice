package com.zcl.practice;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import androidx.multidex.MultiDexApplication;

/**
 * Description Application
 * Author ZhangChenglong
 * Date 17/12/21 11:43
 */

public class App extends MultiDexApplication {
    
    public static final boolean GLOBAL_DEBUG = BuildConfig.DEBUG;
    /** Application context */
    private static Context sAppContext;
    
    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        Fresco.initialize(this);
//        LoadUtil.loadClass(this);
//        HookUtil.hookAms();
    }
    
    public static Context getAppContext() {
        return sAppContext;
    }
}
