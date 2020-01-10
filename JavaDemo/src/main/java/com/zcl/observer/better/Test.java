package com.zcl.observer.better;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-22
 */
public class Test {
    public static void main(String[] args) {
        
        WeatherData weatherData = new WeatherData();
    
        StationDisplay2 display2 = new StationDisplay2(weatherData);
        StationDisplay display = new StationDisplay(weatherData);
        
        
        weatherData.setMeasurements(30f, 80f, 30f);
        
        
        
    }
    
}
