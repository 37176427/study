package com.study.distruptor.IP;

import java.util.concurrent.Callable;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2018/12/29 17:09
 **/
public class ThreadTest implements Callable {
    private int id;

    ThreadTest(int id){
        this.id = id;
    }
    @Override
    public String call() throws Exception {
        System.out.println("开始运行一个线程....当前线程名："+Thread.currentThread().getName() + "  id: "+id);
        Thread.sleep(1000);
        return Thread.currentThread().getName() + id + "?????";
    }
}
