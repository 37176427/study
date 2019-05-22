package com.study.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/22 15:50
 **/
public class Provider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"sample-provider.xml"});
        context.start();
        System.in.read(); //保证服务一直开启 利用输入流的阻塞来模拟
    }
}
