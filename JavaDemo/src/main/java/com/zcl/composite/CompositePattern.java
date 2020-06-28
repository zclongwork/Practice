package com.zcl.composite;

public class CompositePattern {

    public static void main(String[] args) {
        Component root = new Node("XX公司");
        Component software = new Node("软件部");
        Component hardware = new Node("硬件部");

        Component androidSoftware = new Leaf("android");
        Component iosSoftware = new Leaf("ios");
        Component layout = new Leaf("layout");

        root.addChild(software);
        root.addChild(hardware);
        software.addChild(androidSoftware);
        software.addChild(iosSoftware);
        hardware.addChild(layout);

        root.print();
    }
}
