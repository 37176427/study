package com.study.zookeeper.zkclient;

import org.I0Itec.zkclient.*;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/16 17:36
 **/
public class ZkClientBase {

    static final String CONNECT_ADDR = "192.168.194.128:2181,192.168.194.129:2181,192.168.194.130:2181";

    static final int SESSION_OUTTIME = 5000;//ms

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(new ZkConnection(CONNECT_ADDR),1000);
        zkClient.createEphemeral("/temp");
        zkClient.createPersistent("/super/c1",true);
        Object temp = zkClient.readData("temp");

        zkClient.writeData("/temp","value");
        Thread.sleep(1000);
        //实现watch 子节点变化通知父节点
        zkClient.subscribeChildChanges("/super", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {

            }
        });
        zkClient.subscribeDataChanges("/super", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                //数据变更
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //数据删除
            }
        });
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {

            }

            @Override
            public void handleNewSession() throws Exception {

            }

            @Override
            public void handleSessionEstablishmentError(Throwable throwable) throws Exception {

            }
        });
    }
}
