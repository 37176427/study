package com.study.strategy;

/**
 * 描述 ：具体策略通过回调上下文中的方法来获取其所需要的数据 1
 * 作者 ：WYH
 * 时间 ：2019/5/15 17:25
 **/
public class StrategyByContext1 implements IStrategyByContext{

    @Override
    public String say(StrategyContext3 context3) {
        return "使用上下文的策略1"+context3.getMsg();
    }
}
