package com.study.strategy;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/15 17:27
 **/
public class StrategyByContext2 implements  IStrategyByContext {
    @Override
    public String say(StrategyContext3 context3) {
        return "使用上下文的策略2"+context3.getMsg();
    }
}
