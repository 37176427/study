package com.study.distruptor.generate1;

import com.lmax.disruptor.*;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main1 {

    public static void main(String[] args) throws InterruptedException {
        final int bufferSize =1024;
        final int threadNumbers =4;
        final RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(Trade::new,bufferSize ,new YieldingWaitStrategy());

        ExecutorService executors = new ThreadPoolExecutor(threadNumbers ,threadNumbers ,0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        //缓存屏障
        SequenceBarrier sequenceBarrier =ringBuffer.newBarrier();
        //创建消息处理器  消费者
        //BatchEventProcessor<Trade> batchEventProcessor = new BatchEventProcessor<>(ringBuffer,sequenceBarrier,new TradeHandler());
        //告诉生产者 消费者消费到哪了
        //ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
        //把消息处理器提交到线程池
        //executors.submit(batchEventProcessor);
        WorkerPool<Trade> workerPool = new WorkerPool<>(ringBuffer,sequenceBarrier,new IgnoreExceptionHandler(),new TradeHandler());

        //如果存在多个消费者 那重复执行上面3行代码 把TradeHandler换成其它消费者类
        TradeProduer tradeProduer = new TradeProduer(ringBuffer);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            tradeProduer.onData();
        }
        Thread.sleep(1000);
        //batchEventProcessor.halt();//通知事件(或者说消息)处理器 可以结束了（并不是马上结束!!!）
        workerPool.halt();
        executors.shutdown();//终止线程
    }


}
