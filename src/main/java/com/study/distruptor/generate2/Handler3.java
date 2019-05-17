package com.study.distruptor.generate2;

import com.lmax.disruptor.EventHandler;
import generate1.Trade;

public class Handler3 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler3 get Name:" + trade.getName()+"price: " + trade.getPrice()+ ";  instance: " + trade.toString());
    }
}
