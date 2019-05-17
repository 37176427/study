package com.study.Observer;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/15 15:51
 **/
public class Obs2 implements Observer {
    @Override
    public void change(int price) {
        System.out.println("观察者2收到通知：" + price);
    }
}
