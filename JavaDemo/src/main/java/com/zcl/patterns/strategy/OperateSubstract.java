package com.zcl.patterns.strategy;

/**
 * 算法减
 */
public class OperateSubstract implements Strategy {
    @Override
    public int operate(int num1, int num2) {
        return num1 - num2;
    }
}
