package com.zcl.composite;

public class Leaf extends Component {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void print() {
        System.out.println(name);
    }

    @Override
    public void addChild(Component component) {
        System.out.println("叶子节点，没有子节点");
    }

    @Override
    public void removeChild(Component component) {
        System.out.println("叶子节点，没有子节点");
    }

    @Override
    public Component getChild(int index) {
        System.out.println("叶子节点，没有子节点");
        return null;
    }
}