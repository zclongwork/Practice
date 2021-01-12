package com.zcl.visitor;

public class Demo {
    public static void main(String[] args) {
        Animal[] animals = {new Dog(), new Cat(), new Fox(), new Cat(), new Dog(), new Dog()};
        Speaker s = new Speaker();
        /**  改造部分开始  */
        Counter c = new Counter();
        for (Animal animal : animals) {
            c.visit(animal);
        }
        c.log();
        /**   改造部分结束  */
        for (Animal animal : animals) {
            s.visit(animal);
        }
    }
}
