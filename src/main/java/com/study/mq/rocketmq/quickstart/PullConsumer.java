package com.study.mq.rocketmq.quickstart;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/9 15:44
 **/
public class PullConsumer {

    private static final Map<MessageQueue,Long> offseTable = new HashMap<>();

    public static void main(String[] args) throws MQClientException {
        String group_name = "pull_consumer";
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(group_name);
        consumer.setNamesrvAddr("192.168.194.128:9876;192.168.194.129:9876");
        consumer.start();
        //从topicTest这个主题去获取所有的队列（默认4个）
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");
        for (MessageQueue mq : mqs) {
            System.out.println("Consume from the queue :" + mq);
            SINGEL_MQ:
            while (true) {
                try {
                    //从queue中获取数据 从什么位置开始拉取 单次最多拉取32条记录
                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    System.out.println(pullResult);
                    System.out.println(pullResult.getPullStatus());
                    System.out.println();
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            List<MessageExt> list = pullResult.getMsgFoundList();
                            for (MessageExt msg : list) {
                                System.out.println(new String(msg.getBody()));
                            }
                            break;
                        case NO_NEW_MSG:
                            System.out.println("没有新的数据啦....");
                            break SINGEL_MQ;
                        case NO_MATCHED_MSG:
                            System.out.println("没有匹配数据");
                            break;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {

                }

            }

        }
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offseTable.get(mq);
        if (offset !=null){
            return offset;
        }
        return 0;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long nextBeginOffset) {
        offseTable.put(mq,nextBeginOffset);
    }

}
