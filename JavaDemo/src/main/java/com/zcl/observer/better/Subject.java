package com.zcl.observer.better;

/**
 * TODO Description
 *
 * @author zcl
 * @since 2019-11-22
 */
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
