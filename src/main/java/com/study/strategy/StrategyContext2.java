package com.study.strategy;

/**
 * 描述 ：由上下文直接觉得选择哪种策略 的上下文
 * 作者 ：WYH
 * 时间 ：2019/5/15 17:19
 **/
public class StrategyContext2 {

    String say(){
        IStrategy strategy1 = new Strategy1();
        try {
            return strategy1.say();
        }catch (Exception e){
            IStrategy strategy2 = new Strategy2();
            return strategy2.say();
        }
    }
}
