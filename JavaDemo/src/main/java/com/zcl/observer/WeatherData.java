package com.zcl.observer;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-22
 */
public class WeatherData {
    
    private float temperature;
    private float humidity;
    private float pressure;
    
    public float getTemperature() {
        return temperature;
    }
    

    public float getHumidity() {
        return humidity;
    }
    

    
    public float getPressure() {
        return pressure;
    }
    
    
    
    public void measurementsChanged() {
        StationDisplay display = new StationDisplay();
        display.update(getTemperature(), getHumidity(), getPressure());
    }
    
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
    
}
