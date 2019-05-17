package com.study.strategy;

import lombok.Data;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/5/15 17:26
 **/
@Data
class StrategyContext3 {
    private String msg;
    private IStrategyByContext strategyByContext;
    StrategyContext3(String msg,IStrategyByContext strategy){
        this.msg = msg;
        this.strategyByContext = strategy;
    }

    String say(){
        return strategyByContext.say(this);
    }
}
