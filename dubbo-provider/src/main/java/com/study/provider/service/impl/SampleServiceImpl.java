package com.study.provider.service.impl;

import com.study.provider.eneity.User;
import com.study.provider.service.SampleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述 ：服务实现
 * 作者 ：WYH
 * 时间 ：2019/5/22 15:30
 **/

@Service("sampleService")
@com.alibaba.dubbo.config.annotation.Service(protocol = {"dubbo"})
public class SampleServiceImpl implements SampleService {
    @Override
    public String sayHello(String name) {
        return "Hello!" + name;
    }

    @Override
    public List getUser() {
        List list = new ArrayList();
        User u1 = new User();
        u1.setName("Jack");
        u1.setAge(22);
        u1.setSex("m");
        User u2 = new User();
        u2.setName("tom");
        u2.setAge(20);
        u2.setSex("m");
        User u3 = new User();
        u3.setName("rose");
        u3.setAge(19);
        u3.setSex("w");
        list.add(u1);
        list.add(u2);
        list.add(u3);
        return list;
    }

}
