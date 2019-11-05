package com.zcl.patterns.strategy;

/**
 * 策略模式：封装算法，使各个算法可以彼此替换，因此算法的封装类就需要实现同一个接口
 */
public interface Strategy {
    int operate(int num1, int num2);
}
