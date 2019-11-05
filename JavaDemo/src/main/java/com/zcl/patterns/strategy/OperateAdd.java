package com.zcl.patterns.strategy;

/**
 * 算法加
 */
public class OperateAdd implements Strategy {
    @Override
    public int operate(int num1, int num2) {
        return num1 + num2;
    }
}
