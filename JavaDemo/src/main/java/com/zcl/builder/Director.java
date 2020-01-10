package com.zcl.builder;

/**
 * Director（指挥者）导演类
 * 负责调用适当的建造者来组建产品，导演类一般不与产品类发生依赖关系，与导演类直接交互的是建造者类
 * 一般来说，导演类被用来封装程序中易变的部分。
 *
 * @author zcl
 * @since 2019-11-21
 */
public class Director {
    
    
    /**
     * 持有当前需要使用的构建器对象
     */
    private Builder builder;
    /**
     * 构造方法，传入构建器对象
     * @param builder 构建器对象
     */
    public Director(Builder builder) {
        this.builder = builder;
    }
    /**
     * 示意方法，指导构建器构建最终的产品对象
     */
    public ProductCar construct() {
        
        
        
        //通过使用构建器接口来构建最终的产品对象
        return builder.getResult();
    }
}
