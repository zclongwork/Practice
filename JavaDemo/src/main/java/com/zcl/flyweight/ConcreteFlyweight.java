package com.zcl.flyweight;

public class ConcreteFlyweight implements Flyweight {

    private Character intrinsicState = null;

    public ConcreteFlyweight(Character state){
        this.intrinsicState = state;
    }

    @Override

    public void operation(String state) {
        // TODO Auto-generated method stub
        System.out.println("Intrinsic State = " + this.intrinsicState);

        System.out.println("Extrinsic State = " + state);
    }
}