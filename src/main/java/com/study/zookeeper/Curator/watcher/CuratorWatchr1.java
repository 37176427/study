package com.study.zookeeper.Curator.watcher;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/18 17:27
 **/
public class CuratorWatchr1 {

    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";
    static final int SESSION_OUTTIME = 1000;//ms

    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,10);
        //2通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR).sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();
        cf.start();

        //本地缓存
        final NodeCache cache = new NodeCache(cf,"/super",false);
        cache.start(true);
        //触发事件为创建与更新  删除并不触发
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() {
                String msg = cache.getCurrentData().getPath();
                System.out.println(msg);
            }
        });

        //方式2   第三个参数为是否接收(缓存)节点数据 false是不接收
        PathChildrenCache cache2 = new PathChildrenCache(cf,"/super",true);
        //初始化时进行缓存监听
        cache2.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache2.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()){
                    case CHILD_ADDED:{}
                    case CHILD_UPDATED:{}
                    case CHILD_REMOVED:{}
                }
            }
        });
    }
}
