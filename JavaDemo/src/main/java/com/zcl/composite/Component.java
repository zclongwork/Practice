package com.zcl.composite;

public abstract class Component {
    String name;

    public Component(String name) {
        this.name = name;
    }

    public abstract void print();

    public abstract void addChild(Component component);

    public abstract void removeChild(Component component);

    public abstract Component getChild(int index);
}
