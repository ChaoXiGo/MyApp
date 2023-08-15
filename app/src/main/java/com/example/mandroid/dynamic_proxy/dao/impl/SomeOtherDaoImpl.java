package com.example.mandroid.dynamic_proxy.dao.impl;

import com.example.mandroid.dynamic_proxy.dao.SomeDao;

public class SomeOtherDaoImpl implements SomeDao {
    @Override
    public void myMethod() {
        System.out.println("This SomeOtherDaoImpl myMethod~~~");
    }

    @Override
    public String setMethod(String info) {
        System.out.println("方法执行过程");
        return ("方法返回值" + info);
    }
}
