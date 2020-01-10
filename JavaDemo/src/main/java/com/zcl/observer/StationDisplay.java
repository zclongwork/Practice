package com.zcl.observer;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-22
 */
public class StationDisplay {
    
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("StationDisplay 的显示板 temperature：" + temperature
         + " humidity：" + humidity + " pressure：" + pressure);
    }
}
