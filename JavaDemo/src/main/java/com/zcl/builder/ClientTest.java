package com.zcl.builder;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-21
 */
public class ClientTest {
    
    public static void main(String[] args) {
        
        Builder builder = new ConcreteBuilder();
        builder.buildEngine("丰田发动机");
        builder.buildFrame("丰田车架");
        builder.buildWheel("马牌轮胎");
        
        Director director = new Director(builder);
        ProductCar car = director.construct();
        
        System.out.println(car.toString());
    }
}
