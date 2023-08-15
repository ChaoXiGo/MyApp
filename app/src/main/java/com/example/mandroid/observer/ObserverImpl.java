package com.example.mandroid.observer;

public class ObserverImpl implements Observer{
    @Override
    public void notify(String message) {
        System.out.println("收到"+ message);
    }
}
