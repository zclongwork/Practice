package com.zcl.proxy;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {

//        proxy();
        dynamicProxy();
    }

    static void proxy() {
        //创建委托类
        Subject mRealSubject=new RealSubject();
        //创建代理类
        ProxySubject mProxy = new ProxySubject(mRealSubject);
        //由代理类去做具体的操作
        mProxy.doSomething();
    }


    static void dynamicProxy() {
        // 委托类
        Subject mRealSubject = new RealSubject();
        // 委托类classLoader
        ClassLoader mClassLoader = mRealSubject.getClass().getClassLoader();
        // 委托类对应的ProxyHandler
        DynamicProxyHandler mProxyHandler = new DynamicProxyHandler(mRealSubject);
        Class[] mClasses = new Class[]{Subject.class};
        // 代理类
        Subject proxySubject = (Subject) Proxy.newProxyInstance(mClassLoader, mClasses, mProxyHandler);
        // 代理类调用方法
        proxySubject.doSomething();
    }
}
