package com.zcl.visitor;

public class Demo {
    public static void main(String[] args) {
        Animal[] animals = {new Dog(), new Cat(), new Fox(), new Cat(), new Dog(), new Dog()};
        Speaker s = new Speaker();
        Counter c = new Counter();
        for (Animal animal : animals) {
            c.visit(animal);
        }
        c.log();

        for (Animal animal : animals) {
            s.visit(animal);
        }
    }
}
