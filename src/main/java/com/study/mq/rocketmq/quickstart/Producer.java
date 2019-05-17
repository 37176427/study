package com.study.mq.rocketmq.quickstart;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/3/22 16:52
 **/
public class Producer {
    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("quickstart_producer");
        producer.setNamesrvAddr("192.168.194.128:9876;192.168.194.129:9876");
        producer.start();

        for (int i = 0;i<10;i++){
            try {
                Message msg = new Message("TopicPull","TagA",
                        ("Hello RocketMQ"+i).getBytes());
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            }catch (Exception e){
                e.printStackTrace();
                Thread.sleep(1000);
            }

        }
        producer.shutdown();
    }
}
