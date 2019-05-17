package com.study.zookeeper.Curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述 ：curator基本类 工厂模式 创建连接 链式编程
 * 作者 ：WYH
 * 时间 ：2019/4/18 16:38
 **/
public class CuratorBase {
    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";
    static final int SESSION_OUTTIME = 1000;//ms

    public static void main(String[] args) throws Exception {
        //1/重试策略 初始时间1s 重试十次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,10);
        //2通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
        cf.start();
        //节点的创建删除
        cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1","c1VALUE".getBytes());
        cf.delete().deletingChildrenIfNeeded().forPath("/s");
        String c2Value = new String(cf.getData().forPath("/super/c2"));
        cf.setData().forPath("/super/c2","修改内容".getBytes());

        // 异步绑定回调 同时操作多个节点时可将回调函数交给线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground((curatorFramework, curatorEvent) -> {
            curatorEvent.getResultCode();//0-成功 -4端口连接 -110已存在节点 -112会话过期
            curatorEvent.getType();
        },pool);
        //得到所有子节点名
        List<String> list = cf.getChildren().forPath("/super");
        //检查已存在否
        Stat stat = cf.checkExists().forPath("super/s1");



        cf.close();
    }
}
