package com.zcl.visitor;

public class Dog implements Animal {
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
