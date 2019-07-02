package com.study.provider.eneity;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/2 14:58
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sample implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private Map<String,Integer> map;

}
