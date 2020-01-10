package com.zcl.observer.better;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-22
 */
public class StationDisplay implements Observer {
    
    
    
    public StationDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("StationDisplay 的显示板 temperature：" + temperature
                + " humidity：" + humidity + " pressure：" + pressure);
    }
}
