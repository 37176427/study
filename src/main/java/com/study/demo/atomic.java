package com.study.demo;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/12 17:46
 **/
public class atomic implements Runnable {

    int a;

    public void set1() {
        a = 1;
    }

    public void set2() {
        a = 2;
    }

    @Override
    public void run() {
        while (true){
            if (a!=1 && a!=2){
                System.out.println("error: " + a);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("right:" + a);
            }
        }
    }
}
