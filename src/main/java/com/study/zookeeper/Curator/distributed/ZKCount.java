package com.study.zookeeper.Curator.distributed;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * 描述 ：分布式计数
 * 作者 ：WYH
 * 时间 ：2019/5/21 16:48
 **/
public class ZKCount {
    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";
    static final int SESSION_OUTTIME = 1000;//ms

    public static void main(String[] args) throws Exception {
        //1/重试策略 初始时间1s 重试十次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //2通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
        cf.start();

        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(cf,"/super",new RetryNTimes(3,1000));

        //atomicInteger.forceSet(1);
        AtomicValue<Integer> value = atomicInteger.increment();
        //AtomicValue<Integer> atomicValue = atomicInteger.get();
        System.out.println(value.succeeded());
        System.out.println(value.preValue());//原始值
        System.out.println(value.postValue());//最新值
    }

}
