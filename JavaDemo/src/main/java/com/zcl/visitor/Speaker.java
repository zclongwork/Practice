package com.zcl.visitor;

public class Speaker implements Visitor {
    @Override
    public void visit(Animal a) {
        a.accept(this);
    }

    @Override
    public void visit(Dog d) {
        System.out.println("dog wang~");
    }

    @Override
    public void visit(Cat c) {
        System.out.println("cat miao ~");
    }

    @Override
    public void visit(Fox f) {
        System.out.println("fox woo~");
    }
}
