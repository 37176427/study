package com.study.strategy;

/**
 * 描述 ：策略模式的上下文 由客户端选择策略
 * 作者 ：WYH
 * 时间 ：2019/5/15 17:13
 **/
class StrategyContext {

    private IStrategy strategy;

    StrategyContext(IStrategy strategy){
        this.strategy = strategy;
    }
    String say() {
        return strategy.say();
    }
}
