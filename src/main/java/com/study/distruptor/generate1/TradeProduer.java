package com.study.distruptor.generate1;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;

public class TradeProduer {

    EventTranslator<Trade> eventTranslator= new EventTranslator<Trade>() {
        @Override
        public void translateTo(Trade trade, long l) {
        }
    };
    private final RingBuffer<Trade> ringBuffer;

    public TradeProduer(RingBuffer<Trade> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(){
        ringBuffer.publishEvent(eventTranslator);
    }
}
