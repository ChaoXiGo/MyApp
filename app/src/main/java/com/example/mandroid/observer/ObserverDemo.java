package com.example.mandroid.observer;

public interface ObserverDemo {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

