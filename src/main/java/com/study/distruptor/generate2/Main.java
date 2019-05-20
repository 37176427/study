package com.study.distruptor.generate2;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.study.distruptor.generate1.Trade;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception{
        long beginTime=System.currentTimeMillis();
        int bufferSize=1024;
        ExecutorService executor= new ThreadPoolExecutor(8,8,0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        Disruptor<Trade> disruptor = new Disruptor<>(Trade::new,bufferSize,executor, ProducerType.SINGLE,new YieldingWaitStrategy());
        EventHandlerGroup<Trade> eventHandlerGroup =disruptor.handleEventsWith(new Handler1(),new Handler4());
        eventHandlerGroup.then(new Handler3());
        disruptor.start();//启动
        CountDownLatch latch=new CountDownLatch(1);
        executor.submit(new TradePublisher(latch, disruptor));
        latch.await();//等待生产完成
        disruptor.shutdown();
        executor.shutdown();
        System.out.println("总耗时:"+(System.currentTimeMillis()-beginTime));
    }

}
