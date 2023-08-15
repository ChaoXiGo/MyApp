package com.example.mandroid.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverDemoImpl implements ObserverDemo {
    List<Observer> observers = new ArrayList<>();
    String mMessage;
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.notify(mMessage);
        }
    }

    public void setMessage(String message) {
        mMessage = message;
        notifyObservers();
    }
}
