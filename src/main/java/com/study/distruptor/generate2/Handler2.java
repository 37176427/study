package com.study.distruptor.generate2;

import com.lmax.disruptor.EventHandler;
import com.study.distruptor.generate1.Trade;


public class Handler2 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler2 set price2..." + trade.toString());
        Thread.sleep(100);
        trade.setPrice(2);
    }
}
