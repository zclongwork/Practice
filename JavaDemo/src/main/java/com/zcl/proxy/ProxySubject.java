package com.zcl.proxy;

public class ProxySubject implements Subject {

    private Subject mSubject;
    // 代理类持有委托类的引用
    public ProxySubject(Subject realSubject) {
        mSubject = realSubject;
    }

    @Override
    public void doSomething() {
        mSubject.doSomething();
    }
}
