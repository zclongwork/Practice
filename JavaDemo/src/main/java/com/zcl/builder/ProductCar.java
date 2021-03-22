package com.zcl.builder;

/**
 * 最终产品 汽车
 * 假设汽车由；轮子、车架、发动机组成
 *
 * 产品类可以是由一个抽象类与它的不同实现组成，也可以是由多个抽象类与他们的实现组成。
 *
 * @author zcl
 * @since 2019-11-21
 */
public class ProductCar {

    private String engine;
    private String frame;
    private String wheel;

    public String getWheel() {
        return wheel;
    }
    
    public void setWheel(String wheel) {
        this.wheel = wheel;
    }
    
    public String getFrame() {
        return frame;
    }
    
    public void setFrame(String frame) {
        this.frame = frame;
    }
    
    public String getEngine() {
        return engine;
    }
    
    public void setEngine(String engine) {
        this.engine = engine;
    }


    @Override
    public String toString() {
        return "ProductCar{" +
                "engine='" + engine + '\'' +
                ", frame='" + frame + '\'' +
                ", wheel='" + wheel + '\'' +
                '}';
    }
}
