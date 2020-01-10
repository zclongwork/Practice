package com.zcl.builder;

/**
 * 具体建造者
 * 它实现了Builder接口，实现各个部件的具体构造和装配方法，定义并明确它所创建的复杂对象，
 * 提供一个方法返回创建好的复杂产品对象。
 *
 * @author zcl
 * @since 2019-11-21
 */
public class ConcreteBuilder implements Builder {
    
    // 构建器最终构建的产品对象
    private ProductCar resultProduct;
    
    public ConcreteBuilder() {
        resultProduct = new ProductCar();
    }
    
    /**
     * 获取构建器最终构建的产品对象
     *
     * @return 构建器最终构建的产品对象
     */
    @Override
    public ProductCar getResult() {
        return resultProduct;
    }
    
    
    @Override
    public void buildWheel(String wheel) {
        resultProduct.setWheel(wheel);
    }
    
    @Override
    public void buildFrame(String frame) {
        resultProduct.setFrame(frame);
    }
    
    @Override
    public void buildEngine(String engine) {
        resultProduct.setEngine(engine);
    }
}
