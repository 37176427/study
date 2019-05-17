package com.study.strategy;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/15 17:12
 **/
public class Strategy1 implements IStrategy {
    @Override
    public String say() {
        int i = 1/0;
        return "策略1";
    }
}
