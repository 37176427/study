package com.study.distruptor.generate1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Trade {
    private  String id;
    ReentrantLock lock = new ReentrantLock();
    private String name;
    private double price;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        lock.lock();
        System.out.println("被设置值："+name);
        this.name = name;
        System.out.println("设值完成："+name);
        lock.unlock();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
