package com.zcl.visitor;

public interface Visitor {
    void visit(Animal a);
    void visit(Dog d);
    void visit(Cat c);
    void visit(Fox f);
}
