package com.study.distruptor.base;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory {
    public Object newInstance() {
        return new LongEvent();
    }
}
