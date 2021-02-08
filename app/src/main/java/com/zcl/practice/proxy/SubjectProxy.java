package com.zcl.practice.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SubjectProxy implements InvocationHandler {

    private Subject subject;

    public SubjectProxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("proxy", "before can do something");
        Object invoke = method.invoke(subject, args);
        Log.d("proxy", "after can do something");
        return invoke;
    }
}
