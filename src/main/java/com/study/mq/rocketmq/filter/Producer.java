package com.study.mq.rocketmq.filter;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/10 11:08
 **/
public class Producer {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String group_name = "filter_producer";
        DefaultMQProducer producer = new DefaultMQProducer(group_name);
        producer.setNamesrvAddr("192.168.194.128:9876;192.168.194.129:9876");
        producer.start();

        for (int i = 0; i < 100; i++) {
            Message msg = new Message();
            msg.setTopic("TopicFilter");
            msg.setTags("TagA");
            msg.setKeys("OrderID001");
            msg.setBody(("Hello RocketMQ Filter: " + i).getBytes());
            //做过滤 给消息附加属性 可以没有
            msg.putUserProperty("SequenceId", String.valueOf(i));
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }

        producer.shutdown();
    }

}
