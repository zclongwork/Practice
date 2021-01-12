package com.zcl.proxy;

public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("This is real doSomeThing");
    }
}
