package com.study.distruptor.netty;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/1/8 16:02
 **/
public class Client {
    //单例模式
    private static class Inner{
        private static final Client instance = new Client();
    }
    public Client getInstance(){
        return Inner.instance;
    }



}
