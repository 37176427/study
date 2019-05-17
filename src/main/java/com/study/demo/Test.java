package com.study.demo;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/11 17:09
 **/
public class Test {

    private Semaphore semaphore = new Semaphore(10);
    private HashMap<String, Integer> a = new HashMap<>();
    private CountDownLatch countDownLatch = new CountDownLatch(20);

    public static void main(String[] args) throws InterruptedException {

        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if("2".equals(temp)){
                a.remove(temp);
            }
        }
        System.out.println(Arrays.toString(a.toArray()));
//        atomic atomic = new atomic();
//        List<String> list = new ArrayList<>();
//        list.add(0,"helloworld");
//        list.add(1,"helloworld1");

//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                atomic.set2();
//            }
//        }).start();
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                atomic.set1();
//            }
//        }).start();
//        new Thread(atomic).start();
//        Test t = new Test();
//        t.test();
    }

    public void test() throws InterruptedException {
        a.put("k", 0);
        Thread t;
        for (int i = 0; i < 20; i++) {
            t = new myT("t" + i);
            t.start();
        }
        countDownLatch.await();
        System.out.println(a);
    }

    class myT extends Thread {
        myT(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                for (int j = 0; j < 1000; j++) {
                    System.out.println(Thread.currentThread().getName() + "操作" + a.get("k"));
                    a.put("k", a.get("k") + 1);
                    Thread.sleep(10);
                }
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();

            }
        }
    }
}
