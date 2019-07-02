package com.study.provider.service.impl;

import com.study.provider.eneity.Sample;
import com.study.provider.eneity.User;
import com.study.provider.service.SampleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Sample getSample() {
        Map<String,Integer> map = new HashMap<>();
        map.put("person0",21);
        map.put("person1",17);
        return new Sample("person2",30,map);
    }

}
