package com.zcl.visitor;

public class Cat implements Animal {
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
