package com.study.zookeeper.base;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/12 11:48
 **/
public class ZookeeperBase {
    //zk地址
    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";
    //session超时时间
    static final int SESSION_OUTTIME = 5000;
    //阻塞程序执行 用于等待zk连接成功发送成功信号
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
    static final CountDownLatch connectedSemaphore2 = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //获取事件的状态
                Event.KeeperState keeperState = watchedEvent.getState();
                Event.EventType eventType = watchedEvent.getType();
                //如果是建立连接
                if (Event.KeeperState.SyncConnected == keeperState){
                    if (Event.EventType.None == eventType){
                        //如果连接建立成功 则发送信号量 让后续阻塞程序继续执行
                        System.out.println("zk 建立连接");
                        connectedSemaphore.countDown();
                    }
                }
            }
        });
        connectedSemaphore.await();
        //zk.create("/testRoot","testRoot1111".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        byte[] data = zk.getData("/testRoot", false, null);
        zk.exists("/p",true);
        System.out.println(new String(data));
        zk.close();
    }


}
