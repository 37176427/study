package com.study.Observer;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/15 15:45
 **/
public interface Product {
    //注册为一个观察者
     void registerObserver(Observer observer);

    //取消观察者
     void removeObserver(Observer observer);

    //通知所有观察者更新信息
     void notifyObservers();
}
