package com.zcl.builder;

/**
 * 抽象建造者
 *
 * 它为创建一个产品Product对象的各个部件指定抽象接口，在该接口中一般声明两类方法，
 * 一类方法是buildPartX()，它们用于创建复杂对象的各个部件；
 * 另一类方法是getResult()，它们用于返回复杂对象。
 * Builder既可以是抽象类，也可以是接口。
 *
 * @author zcl
 * @since 2019-11-21
 */
public interface Builder {
    
    void buildWheel(String wheel);
    
    void buildFrame(String frame);
    
    void buildEngine(String engine);
    
    ProductCar getResult();
}
