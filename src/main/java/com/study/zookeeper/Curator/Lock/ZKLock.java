package com.study.zookeeper.Curator.Lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * 描述 ：分布式锁
 * 作者 ：WYH
 * 时间 ：2019/4/30 17:27
 **/
public class ZKLock {
    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";
    static final int SESSION_OUTTIME = 1000;//ms

    public static void main(String[] args) {
        //1/重试策略 初始时间1s 重试十次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //2通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
        cf.start();
        //节点上临时的锁
        InterProcessMutex lock = new InterProcessMutex(cf, "/super");
        final CountDownLatch countdown = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    lock.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
