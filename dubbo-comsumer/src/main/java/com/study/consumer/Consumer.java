package com.study.consumer;

import com.study.provider.eneity.Sample;
import com.study.provider.service.SampleService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/22 16:01
 **/
public class Consumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"sample-consumer.xml"});
        context.start();

        SampleService sampleService = (SampleService) context.getBean("sampleService");
        String hello = sampleService.sayHello("tom");
        System.out.println(hello);
        Sample sample = sampleService.getSample();
        System.out.println(sample.getAge());
        System.out.println(sample.getName());
        System.out.println(sample.getMap().get("person0"));
        System.in.read();
    }
}
