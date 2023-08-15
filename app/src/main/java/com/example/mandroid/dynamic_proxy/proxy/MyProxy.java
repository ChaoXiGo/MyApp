package com.example.mandroid.dynamic_proxy.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy {
    String TAG = "MyProxy";
    private Object target;

    public MyProxy(Object target) {
        this.target = target;
    }

    public <T> T create(final Class<T> demo) {
        return (T) Proxy.newProxyInstance(demo.getClassLoader(),
                new Class<?>[]{demo},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Log.d(TAG, "before~~~~");
                        Object result = method.invoke(target, args);
                        Log.d(TAG, "after~~~~");
                        return result;
                    }
                });
    }
}