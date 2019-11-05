package com.zcl.patterns.strategy;

/**
 * 策略模式demo
 */
public class StrategyPatternDemo {
    
    public static void main(String[] args) {
    
        Calculator add = new Calculator(new OperateAdd());
        System.out.println("10 + 5 = " + add.doOperate(10, 5));
        
        Calculator substract = new Calculator(new OperateSubstract());
        System.out.println("10 + 5 = " + substract.doOperate(10, 5));
    
    }
}
