package com.study.distruptor.generate2;

import com.lmax.disruptor.EventHandler;
import com.study.distruptor.generate1.Trade;

public class Handler4 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws InterruptedException {
        System.out.println("handler4: set name4 ... "+trade.toString());
        Thread.sleep(100);
        trade.setName("name4");
    }
}
