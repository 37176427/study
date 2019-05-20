package com.study.distruptor.generate2;

import com.lmax.disruptor.EventHandler;
import com.study.distruptor.generate1.Trade;

/**
 * 消费者类型1
 */
public class Handler1 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler1 set name1.."+ trade.toString());
        Thread.sleep(100);
        trade.setName("name1");
    }
}
