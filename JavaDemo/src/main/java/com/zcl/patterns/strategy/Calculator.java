package com.zcl.patterns.strategy;

/**
 * 上下文角色，起承上启下封装作用，屏蔽高层模块对策略、算法的直接访问，封装可能存在的变化。
 */
public class Calculator {
    private Strategy strategy;
    
    public Calculator(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public int doOperate(int num1, int num2) {
        return strategy.operate(num1, num2);
    }
}
