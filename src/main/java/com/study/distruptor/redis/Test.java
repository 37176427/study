package com.study.distruptor.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/2/13 17:02
 **/
public class Test {
    public static void main(String[] args) {
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.194.128",7001));
        hostAndPorts.add(new HostAndPort("192.168.194.128",7002));
        hostAndPorts.add(new HostAndPort("192.168.194.128",7003));
        hostAndPorts.add(new HostAndPort("192.168.194.128",7004));
        hostAndPorts.add(new HostAndPort("192.168.194.128",7005));
        hostAndPorts.add(new HostAndPort("192.168.194.128",7006));
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        jedisCluster.set("jk1","haha");
        System.out.println(jedisCluster.get("k1"));
        jedisCluster.close();
    }

}
