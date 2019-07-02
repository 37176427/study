package com.study.provider.service;

import java.util.List;

/**
 * 描述 ：服务接口 消费方要想使用必须有服务提供方的接口
 *          路径、包名也需要相同 这是个坑
 * 作者 ：WYH
 * 时间 ：2019/5/22 15:29
 **/
public interface SampleService {
    String sayHello(String name);
    List getUser();

}
