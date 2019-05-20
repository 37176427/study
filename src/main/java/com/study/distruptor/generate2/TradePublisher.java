package com.study.distruptor.generate2;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.study.distruptor.generate1.Trade;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TradePublisher implements Runnable {

    Disruptor<Trade> disruptor;
    private CountDownLatch latch;

    public TradePublisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        TradeEventTranslator tradeTransloator = new TradeEventTranslator();
        for (int i = 0; i < 3; i++) {
            disruptor.publishEvent(tradeTransloator);
        }
        latch.countDown();
    }


    class TradeEventTranslator implements EventTranslator<Trade> {

        private Random random = new Random();

        @Override
        public void translateTo(Trade event, long sequence) {
            event.setPrice(random.nextDouble() * 9999);
        }
    }
}