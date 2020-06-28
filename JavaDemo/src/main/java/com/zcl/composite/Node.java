package com.zcl.composite;

import java.util.ArrayList;
import java.util.List;

public class Node extends Component {

    private List<Component> list = new ArrayList<>();

    public Node(String name) {
        super(name);
    }


    @Override
    public void print() {
        System.out.println("node: " + name);
        for (Component component:list) {
            component.print();
        }
    }

    @Override
    public void addChild(Component component) {
        list.add(component);
    }

    @Override
    public void removeChild(Component component) {
        list.remove(component);
    }

    @Override
    public Component getChild(int index) {
        return list.get(index);
    }
}
