package com.study.strategy;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/15 17:14
 **/
public class Main {
    public static void main(String[] args) {
        //1
        //IStrategy strategy = new Strategy2();
        //StrategyContext context = new StrategyContext(strategy);
        //2
        //System.out.println(new StrategyContext2().say());
        //3
        IStrategyByContext strategyByContext1 = new StrategyByContext1();
        IStrategyByContext strategyByContext2 = new StrategyByContext2();
        StrategyContext3 context3 = new StrategyContext3("aaaa",strategyByContext1);

        System.out.println(context3.say());
        context3 = new StrategyContext3("bbbb",strategyByContext2);
        System.out.println(context3.say());
    }
}
