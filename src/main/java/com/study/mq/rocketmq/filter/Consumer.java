package com.study.mq.rocketmq.filter;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/10 11:07
 **/
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        String group_name = "filter_consumer";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group_name);
        consumer.setNamesrvAddr("192.168.194.128:9876;192.168.194.129:9876");
        //使用java代码 在服务器做消息过滤
        String filterCode = MixAll.file2String("D:\\workspace\\MQ\\src\\main\\java\\rocketmq\\filter\\MessageFilterImpl.java");
        System.out.println(filterCode);
        consumer.subscribe("TopicFilter","rocketmq.filter.MessageFilterImpl",filterCode);

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                MessageExt messageExt = msgs.get(0);
                byte[] body = messageExt.getBody();
                String msgBody = new String(body);
                System.out.println(Thread.currentThread().getName() + "收到消息：" + msgBody);

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //Consumer对象在使用之前必须要初始化一次
        consumer.start();
        System.out.println("Consumer 已启动");

    }
}
