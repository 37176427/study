package com.study.Observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/15 15:50
 **/
public class Obs1 implements Observer {
//    @Override
    public void change(int price) {
        System.out.println("观察者1收到通知 变化了价格:" + price);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Cup){
            Cup cup = (Cup) o;
            System.out.println("价格已更改为：" + arg + "p:" + cup.getPrice());
        }
    }
}
