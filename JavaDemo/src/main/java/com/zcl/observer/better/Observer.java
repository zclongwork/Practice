package com.zcl.observer.better;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-22
 */
public interface Observer {
    public void update(float temp, float humidity, float pressure);
}
