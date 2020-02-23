package com.zcl.practice.ioc;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ListenerInvocationHandler implements InvocationHandler {
    private Object activity;
    private Method activityMethod;

    public ListenerInvocationHandler(Object context, Method method) {
        activity = context;
        activityMethod = method;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Log.d("proxy", "proxy");

        return activityMethod.invoke(activity, args);
    }
}
