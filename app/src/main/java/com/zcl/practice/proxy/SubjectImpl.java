package com.zcl.practice.proxy;

import android.util.Log;

public class SubjectImpl implements Subject {
    @Override
    public void hello(String param) {
        Log.d("proxy", "hello: " + param);
    }
}
