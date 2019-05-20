package com.study.distruptor.base;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LongEventMain {

    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        LongEventFactory factory = new LongEventFactory();
        int buffSize = 1024;
        //创建disruptor
        Disruptor<LongEvent> disruptor =
                new Disruptor<LongEvent>(factory, buffSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        // 连接消费事件方法
        disruptor.handleEventsWith(new LongEventHandler());
        RingBuffer<LongEvent> ringBuffer = disruptor.start();
        LongEventProducerWithTranslator l = new LongEventProducerWithTranslator(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (int i = 0; i < 100; i++) {
            byteBuffer.putLong(0,i);
            l.onData(byteBuffer);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disruptor.shutdown();
        executor.shutdown();

    }
}
