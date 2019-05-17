package com.study.Observer;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/15 15:46
 **/
public class Cup extends Observable {
    private int price;
    //维护一个观察者列表
    private List<Observer> observers = new ArrayList<>();

    Cup(int price) {
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
        this.setChanged();//通知，数据已改变
        //价格变动 通知观察者
        this.notifyObservers();
    }
    public int getPrice(){
        return price;
    }

//    @Override
//    public void registerObserver(Observer observer) {
//        observers.add(observer);
//    }
//
//    @Override
//    public void removeObserver(Observer observer) {
//        observers.remove(observer);
//    }

//    @Override
//    public void notifyObservers() {
//        for (Observer observer : observers) {
//            observer.change(price);
//        }
//    }


}
