package com.study.demo;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/12 16:02
 **/
public class Consumer implements Runnable{

    private Queue<String> queue1;
    private Queue<String> queue2;

    Consumer(Queue<String> queue1, Queue<String> queue2) {
        this.queue1 = queue1;
        this.queue2 = queue2;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (queue2) {
                System.out.println("锁住了2 想要锁1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (queue1){}
            }
        }
    }
}
