package com.zcl.practice;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Description Application
 * Author ZhangChenglong
 * Date 17/12/21 11:43
 */

public class App extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        
    }
}
