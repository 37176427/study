package com.study.zookeeper.Curator.distributed;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Random;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/21 17:47
 **/
public class ZKBarrier1 {
    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";
    static final int SESSION_OUTTIME = 1000;//ms

    public static void main(String[] args) {
        //1/重试策略 初始时间1s 重试十次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //2通过工厂创建连接

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
                    cf.start();
                    //双重屏障 同时开始同时结束
                    DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(cf, "/super", 5);
                    Thread.sleep(1000 * (new Random().nextInt(3)));
                    System.out.println(Thread.currentThread().getName()+"已经准备好");
                    barrier.enter();
                    System.out.println("同时开始运行.....");
                    Thread.sleep(1000 * (new Random().nextInt(3)));
                    System.out.println(Thread.currentThread().getName()+"运行完毕");
                    barrier.leave();
                    System.out.println("同时退出运行...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"t"+i).start();
        }

    }
}
