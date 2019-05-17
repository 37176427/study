package com.study.distruptor.base;

import com.lmax.disruptor.EventHandler;

/**
 * 消费者 也叫事件处理器
 */

public class LongEventHandler implements EventHandler<LongEvent> {

    public void onEvent(LongEvent longEvent, long l, boolean b){
        System.out.println(longEvent.getValue());
    }

}