package com.example.mandroid.observer;

public class ObserverMain {
    public static void main(String[] args) {
        ObserverDemo observerDemo = (ObserverDemo) new ObserverDemoImpl();
        Observer observer1 = (Observer) new ObserverImpl();
        Observer observer2 = (Observer) new ObserverImpl();

        observerDemo.addObserver(observer1);
        observerDemo.addObserver(observer2);

    }
}
