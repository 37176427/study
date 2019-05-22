package com.study.zookeeper.Curator.distributed;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/22 13:55
 **/
public class ZKBarrier2 {

    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";
    static final int SESSION_OUTTIME = 1000;//ms

    static DistributedBarrier barrier = null;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,10);
                    CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
                    cf.start();

                    barrier = new DistributedBarrier(cf,"/super");
                    System.out.println(Thread.currentThread().getName()+"设置barrier！");
                    barrier.setBarrier();//设置
                    barrier.waitOnBarrier();//等待
                    System.out.println(Thread.currentThread().getName()+"开始执行程序");
                }catch (Exception ignored){
                }

            },"t"+i).start();
        }

        Thread.sleep(5000);
        barrier.removeBarrier();//移除后 所有设置了障碍并等待的开始执行
    }
}
