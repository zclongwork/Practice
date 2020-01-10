package com.zcl.observer.better;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-22
 */
public class StationDisplay2 implements Observer {
    
    
    
    public StationDisplay2(Subject weatherData) {
        weatherData.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
    
        try {
            System.out.println("我要阻塞5秒中");
            
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        System.out.println("StationDisplay2 的显示板 temperature：" + temperature
                + " humidity：" + humidity + " pressure：" + pressure);
    }
}
